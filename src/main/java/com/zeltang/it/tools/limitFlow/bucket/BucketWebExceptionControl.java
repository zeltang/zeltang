package com.zeltang.it.tools.limitFlow.bucket;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * AOP全局异常处理
 */
@RestControllerAdvice
public class BucketWebExceptionControl {

    /**
     * String返回类型也可以替换为一个封装好的返回类
     * @param e
     * @return
     */
    @ExceptionHandler(BucketException.class)
    public String APIExceptionHandler(BucketException e) {
        return e.getMessage();
    }
}
