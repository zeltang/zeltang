package com.zeltang.it.tools.functionalInterface;

import java.util.function.Consumer;

/**
 * 注解表明函数式接口：支持函数式编程
 */
@FunctionalInterface
public interface PresentOrElseHandler<T extends Object> {
    void presentOrElseHandler(Consumer<? super T> action, Runnable falseHandler);
}
