package com.zeltang.it.tools.annotation.redis;

import com.zeltang.it.config.RedissonConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Service
public class LockService {

//    @Autowired
//    private RedissonClient redissonClient;

    public <T> T executeWithLockThrows(String key, int waitTime, TimeUnit unit, SupplierThrow<T> supplier) throws Throwable {
        RedissonClient redissonClient = RedissonConfig.getInstance();
        RLock lock = redissonClient.getLock(key);
        boolean lockSuccess = lock.tryLock(waitTime, unit);
        if (!lockSuccess) {
            throw new Exception("请求太频繁了，请稍后再试哦~~");
        }
        try {
            return supplier.get();//执行锁内的代码逻辑
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public <T> T executeWithLock(String key, int waitTime, TimeUnit unit, Supplier<T> supplier) throws Throwable {
        return executeWithLockThrows(key, waitTime, unit, supplier::get);
    }

    public <T> T executeWithLock(String key, Supplier<T> supplier) throws Throwable {
        return executeWithLock(key, -1, TimeUnit.MILLISECONDS, supplier);
    }

    @FunctionalInterface
    public interface SupplierThrow<T> {

        /**
         * Gets a result.
         *
         * @return a result
         */
        T get() throws Throwable;
    }
}
