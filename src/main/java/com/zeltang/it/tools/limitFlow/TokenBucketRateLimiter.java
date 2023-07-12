package com.zeltang.it.tools.limitFlow;

import com.zeltang.it.config.RedissonConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

/**
 *
 */
@Slf4j
public class TokenBucketRateLimiter {
    public static final String KEY = "TokenBucketRateLimiter:";

    /**
     * 阈值
     */
    private Long limitNum;
    /**
     * 添加令牌的速率，单位：个/秒
     */
    private Long tokenRate;

    public TokenBucketRateLimiter(Long limitNum, Long tokenRate) {
        this.limitNum = limitNum;
        this.tokenRate = tokenRate;
    }

    /**
     * 限流算法
     */
    public boolean triggerLimit(String path){
        RedissonClient redissonClient= RedissonConfig.getInstance();
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(KEY+path);
        // 初始化，设置速率模式，速率，间隔，间隔单位
        rateLimiter.trySetRate(RateType.OVERALL, limitNum, tokenRate, RateIntervalUnit.SECONDS);
        // 获取令牌
        return rateLimiter.tryAcquire();
    }
}
