package com.zeltang.it.tools.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;

public class OperaUtils {

    @Autowired
    private RedisTemplate template;

    String lua1 = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private void test01 () {

        Object execute = template.execute(new DefaultRedisScript<Long>(lua1, Long.class), Collections.singletonList("lock"), "uuid");

        // 解锁脚本
        DefaultRedisScript<Object> unlockScript = new DefaultRedisScript();
        unlockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lockDel.lua")));

        // 执行lua脚本解锁
        template.execute(unlockScript, Collections.singletonList("keyName"), "value");


    }


}
