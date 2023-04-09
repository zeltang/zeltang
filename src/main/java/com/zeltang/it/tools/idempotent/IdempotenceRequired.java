package com.zeltang.it.tools.idempotent;

import java.lang.annotation.*;

/**
 * 定义一个注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdempotenceRequired {
}
