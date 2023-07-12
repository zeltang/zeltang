package com.zeltang.it.tools.limitFlow;

import com.zeltang.it.config.RedissonConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 滑动窗口：使用Redis的有序集合（zset）结构
 * 使用时间戳作为score和member，有请求过来的时候，就把当前时间戳添加到有序集合里。那么窗口之外的请求，可以根据窗口大小，计算出起始时间戳，删除窗口外的请求。这样，有序集合的大小，就是我们这个窗口的请求数了。
 */
@Slf4j
public class SlidingWindowRateLimiter {
    public static final String KEY = "slidingWindowRateLimiter:";

    /**
     * 限制数量
     */
    private Long limitNum;

    /**
     * 窗口大小（单位：秒）
     */
    private Long windowSize;

    public SlidingWindowRateLimiter(Long limitNum, Long windowSize) {
        this.limitNum = limitNum;
        this.windowSize = windowSize;
    }

    public Boolean triggerLimit(String path) {
        RedissonClient redissonClient = RedissonConfig.getInstance();
        //窗口计数
        RScoredSortedSet<Long> counter = redissonClient.getScoredSortedSet(KEY + path);
        //使用分布式锁，避免并发设置初始值的时候，导致窗口计数被覆盖
        RLock rLock = redissonClient.getLock(KEY + "LOCK:" + path);
        try {
            rLock.lock(200, TimeUnit.MILLISECONDS);
            // 当前时间戳
            long currentTimestamp = System.currentTimeMillis();
            // 窗口起始时间戳
            long windowStartTimestamp = currentTimestamp - windowSize * 1000;
            // 移除窗口外的时间戳，左闭右开
            counter.removeRangeByScore(0, true, windowStartTimestamp, false);
            // 将当前时间戳作为score,也作为member，
            // TODO:这里还有一个小的可以完善的点，zset在member相同的情况下，是会覆盖的，也就是说高并发情况下，时间戳可能会重复，那么就有可能统计的请求偏少，这里可以用时间戳+随机数来缓解，也可以生成唯一序列来解决，比如UUID、雪花算法等等。
            counter.add(currentTimestamp, currentTimestamp);
            //使用zset的元素个数，作为请求计数
            long count = counter.size();
            // 判断时间戳数量是否超过限流阈值
            if (count > limitNum) {
                System.out.println("[triggerLimit] path:" + path + " count:" + count + " over limit:" + limitNum);
                return true;
            }
            return false;
        } finally {
            rLock.unlock();
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30, 50, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));

        SlidingWindowRateLimiter slidingWindowRateLimiter = new SlidingWindowRateLimiter(10L, 1L);
        //模拟在不同时间片内的请求
        for (int i = 0; i < 8; i++) {
            try {
                CountDownLatch countDownLatch = new CountDownLatch(20);
                for (int j = 0; j < 20; j++) {
                    threadPoolExecutor.execute(() -> {
                        boolean isLimit = slidingWindowRateLimiter.triggerLimit("/test");
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
