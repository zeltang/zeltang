package com.zeltang.it.tools.thread.pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * 线程池
 */
@Slf4j
public class ThreadPoolTest {

    @Test
    public void test01 () {
        buildPool(new ThreadPoolExecutor.AbortPolicy());
    }

    public void buildPool (RejectedExecutionHandler handler) {
        int coreSize = 3;
        int maxSize = 6;
        long keepAliveTime = 2;
        TimeUnit unit = TimeUnit.SECONDS;
        //创建工作队列，用于存放提交的等待执行任务
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);
        ThreadPoolExecutor executor;
        try {
            executor = new ThreadPoolExecutor(coreSize, maxSize, keepAliveTime, unit, workQueue, handler);
            for (int i = 0; i < 9; i++) {
                //提交任务的索引
                final int index = (i + 1);
                executor.submit(() -> {
                    //线程打印输出
                    System.out.println("大家好，我是线程：" + index);
                    try {
                        //模拟线程执行时间，10s
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                //每个任务提交后休眠500ms再提交下一个任务，用于保证提交顺序
                Thread.sleep(500);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
