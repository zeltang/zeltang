package com.zeltang.it.tools.functionalInterface;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;

/**
 * 使用函数式接口：先定义统一调用类
 *  首先定义一个ServerExecutor
 */
@Log4j2
public class ServerExecutor {
    //不需要返回值的
    public static void execute(Runnable runnable) {
        try {
            //调用前和调用后可以做一些处理，比如熔断、日志等等
            doSome();
            //调用前和调用后可以做一些处理，比如熔断、日志等等
        } catch (Exception e) {
            log.error("RPC error: ", e);
        }
    }

    //需要返回值的
    public static <T> T executeAndReturn(Callable<T> callable) {
        try {
            //调用前和调用后可以做一些处理，比如熔断、日志等等
            return callable.call();
        } catch (Exception e) {
            log.error("RPC invoke error", e);
        }
        return null;
    }




    private static void doSome() {

    }
}
