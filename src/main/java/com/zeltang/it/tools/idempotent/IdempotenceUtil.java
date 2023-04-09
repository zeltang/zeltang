package com.zeltang.it.tools.idempotent;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class IdempotenceUtil {
    @Resource
    private RedisTemplate template;
    /**
     * 生成幂等号
     * @return
     */
    public String generateId() {
        String uuid = UUID.randomUUID().toString();
        String uId= Base64Util.encode(uuid).toLowerCase();
        template.opsForValue().set(uId, "1", 1800, TimeUnit.SECONDS);
        return uId;
    }

    /**
     * 从Header里面获取幂等号
     * @return
     */
    public String getHeaderIdempotenceId(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String idempotenceId=request.getHeader("idempotenceId");
        return idempotenceId;
    }
}
