package com.zeltang.it.tools.idempotent;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 切面
 */
@Aspect
@Slf4j
@Component
public class IdempotenceSupportAdvice {
    @Resource
    private Idempotence idempotence;
    @Resource
    IdempotenceUtil idempotenceUtil;

    /**
     * 拦截有@IdempotenceRequired 注解 的方法。
     */
    @Pointcut("@annotation(xxx.xxx.IdempotenceRequired)")
    public void idempotenceMethod(){}

    @AfterThrowing(value = "idempotenceMethod()()",throwing = "e")
    public void afterThrowing(Throwable e){
        if(!(e instanceof Exception)) {
            // 从HTTP header中获取幂等号idempotenceId
            String idempotenceId = idempotenceUtil.getHeaderIdempotenceId();
            idempotence.record(idempotenceId, 1800);
        }
    }

    @Around(value = "idempotenceMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 从HTTP header中获取幂等号idempotenceId
        String idempotenceId = idempotenceUtil.getHeaderIdempotenceId();
        if(StringUtils.isEmpty(idempotenceId)){
            //不存在幂等号则不进行额外操作
            return joinPoint.proceed();
        }
        // 前置操作 幂等号是否存在
        boolean existed = idempotence.check(idempotenceId);
        if (!existed) {
            throw new Exception("{success:false,message:操作重复，请重新输入幂等号重试！,data:-2}");
        }
        //删除幂等号
        idempotence.delete(idempotenceId);
        Object result = joinPoint.proceed();

        return result;
    }
}
