package com.zeltang.it.tools.idempotent;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@EnableRedisRepositories
public class RedisIdempotence implements Idempotence {

    @Resource
    private RedisTemplate template;

    @Resource
    private DempotenceRepository repository;

    @Override
    public boolean check(String idempotenceId) {
        return template.hasKey(idempotenceId);
    }

    @Override
    public void record(String idempotenceId) {
        template.opsForValue().setIfAbsent(idempotenceId, idempotenceId);
    }

    @Override
    public void record(String idempotenceId, Integer time) {
        template.opsForValue().setIfAbsent(idempotenceId, idempotenceId, time, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String idempotenceId) {
        template.delete(idempotenceId);
    }
}
