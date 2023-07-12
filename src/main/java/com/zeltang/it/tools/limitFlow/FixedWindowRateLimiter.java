package com.zeltang.it.tools.limitFlow;

import com.zeltang.it.config.RedissonConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 固定窗口：在每个窗口期内，通过incrementAndGet操作来统计请求的数量。一旦窗口期结束，我们可以利用Redis的键过期功能来自动重置计数。
 */
@Slf4j
public class FixedWindowRateLimiter {
    public static final String KEY = "fixedWindowRateLimiter:";

    /**
     * 限制数量
     */
    private Long limitNum;

    /**
     * 窗口大小（单位：秒）
     */
    private Long windowSize;

    public FixedWindowRateLimiter(Long limitNum, Long windowSize) {
        this.limitNum = limitNum;
        this.windowSize = windowSize;
    }

    public Boolean triggerLimit (String path) {
        RedissonClient redissonClient = RedissonConfig.getInstance();
        //加分布式锁，防止并发情况下窗口初始化时间不一致问题
        RLock lock = redissonClient.getLock(KEY + "LOCK:" + path);
        try {
            lock.lock(100, TimeUnit.MILLISECONDS);
            String redisKey = KEY + path;
            RAtomicLong counter = redissonClient.getAtomicLong(redisKey);
            // 计数
            long count = counter.incrementAndGet();
            //如果为1的话，就说明窗口刚初始化
            if (count == 1) {
                // 直接设置过期时间，作为窗口
                counter.expire(windowSize, TimeUnit.SECONDS);
            }
            //触发限流
            if (count > limitNum) {
                //触发限流的不记在请求数量中
                counter.decrementAndGet();
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
        return false;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 50, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));
        FixedWindowRateLimiter fixedWindowRateLimiter = new FixedWindowRateLimiter(10L,60L);
        //模拟不同窗口内的调用
        for (int i = 0; i < 3; i++) {
            try {
                CountDownLatch countDownLatch = new CountDownLatch(20);
                //20个线程并发调用
                for (int j = 0; j < 20; j++) {
                    threadPoolExecutor.execute(() -> {
                        boolean isLimit = fixedWindowRateLimiter.triggerLimit("/test");
                        System.out.println(isLimit);
                        countDownLatch.countDown();
                    });
                }
                countDownLatch.await();
                //休眠1min
                TimeUnit.MINUTES.sleep(1);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
