package com.zeltang.it.tools.functionalInterface;

/**
 * 注解表明函数式接口：支持函数式编程
 */
@FunctionalInterface
public interface BranchHandler {
    void trueOrFalseHandler (Runnable trueHandler, Runnable falseHandler);
}
