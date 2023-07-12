package com.zeltang.it.tools.limitFlow;

import com.zeltang.it.config.RedissonConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.util.concurrent.*;

/**
 * 漏桶算法：同滑动窗口类似，使用ZREMRANGEBYSCORE命令来删除旧的请求
 *
 * 进水就不用多说了，请求进来，判断桶有没有满，满了就拒绝，没满就往桶里丢请求。出水怎么办呢？得保证稳定速率出水，可以用一个定时任务，来定时去删除旧的请求。
 */
@Slf4j
public class LeakyBucketRateLimiter {
    private RedissonClient redissonClient = RedissonConfig.getInstance();
    private static final String KEY_PREFIX = "LeakyBucket:";

    /**
     * 桶的大小
     */
    private Long bucketSize;
    /**
     * 漏水速率，单位:个/秒
     */
    private Long leakRate;

    public LeakyBucketRateLimiter(Long bucketSize, Long leakRate) {
        this.bucketSize = bucketSize;
        this.leakRate = leakRate;
        // 这里直接用ScheduledExecutorService启动了一个定时任务，1s跑一次，当然集群环境下，每台机器都跑一个定时任务，对性能是极大的浪费，而且不好管理，我们可以用分布式定时任务，比如xxl-job去执行leakWater。
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::leakWater, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 漏水
     */
    public void leakWater() {
        // 这里用了RSet来存储path，这样一来，一个定时任务，就可以搞定所有path对应的桶的出水，而不用每个桶都创建一个一个定时任务。
        RSet<String> pathSet=redissonClient.getSet(KEY_PREFIX+":pathSet");
        //遍历所有path,删除旧请求
        for(String path:pathSet){
            String redisKey = KEY_PREFIX + path;
            RScoredSortedSet<Long> bucket = redissonClient.getScoredSortedSet(KEY_PREFIX + path);
            // 获取当前时间
            long now = System.currentTimeMillis();
            // 删除旧的请求
            bucket.removeRangeByScore(0, true,now - 1000 * leakRate,true);
        }
    }

    /**
     * 限流
     */
    public boolean triggerLimit(String path) {
        //加锁，防止并发初始化问题
        RLock rLock = redissonClient.getLock(KEY_PREFIX + "LOCK:" + path);
        try {
            rLock.lock(100,TimeUnit.MILLISECONDS);
            String redisKey = KEY_PREFIX + path;
            RScoredSortedSet<Long> bucket = redissonClient.getScoredSortedSet(redisKey);
            //这里用一个set，来存储所有path
            RSet<String> pathSet=redissonClient.getSet(KEY_PREFIX+":pathSet");
            pathSet.add(path);
            // 获取当前时间
            long now = System.currentTimeMillis();
            // 检查桶是否已满
            if (bucket.size() < bucketSize) {
                // 桶未满，添加一个元素到桶中
                bucket.add(now,now);
                return false;
            }
            // 桶已满，触发限流
            System.out.println("[triggerLimit] path:"+path+" bucket size:"+bucket.size());
            return true;
        }finally {
            rLock.unlock();
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30, 50, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));

        LeakyBucketRateLimiter leakyBucketRateLimiter = new LeakyBucketRateLimiter(10L, 1L);
        for (int i = 0; i < 8; i++) {
            try {
                CountDownLatch countDownLatch = new CountDownLatch(20);
                for (int j = 0; j < 20; j++) {
                    threadPoolExecutor.execute(() -> {
                        boolean isLimit = leakyBucketRateLimiter.triggerLimit("/test");
                        System.out.println(isLimit);
                        countDownLatch.countDown();
                    });
                }
                countDownLatch.await();
                //休眠10s
                TimeUnit.SECONDS.sleep(10L);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
