package com.zeltang.it.tools.limitFlow.bucket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 */
@Target({ElementType.METHOD})
//METHOD代表是用在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface BucketAnnotation {
}
