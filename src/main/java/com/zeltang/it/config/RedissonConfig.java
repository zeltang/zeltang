package com.zeltang.it.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 单例模式获取RedissonClient，这里就不注册成bean了，跑单测太慢
 */
public class RedissonConfig {
    private static final String REDIS_ADDRESS = "redis://127.0.0.1:6379";

    private static volatile RedissonClient redissonClient;

    public static RedissonClient getInstance(){
        if (redissonClient==null){
            synchronized (RedissonConfig.class){
                if (redissonClient==null){
                    Config config = new Config();
                    config.useSingleServer().setAddress(REDIS_ADDRESS);
                    redissonClient = Redisson.create(config);
                    return redissonClient;
                }
            }
        }
        return redissonClient;
    }
}
