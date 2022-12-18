package com.zeltang.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicStampedReference;

public class Demo2 {

    private static final Logger log = LoggerFactory.getLogger(Demo2.class);
    private static AtomicStampedReference<Integer> count = new AtomicStampedReference<>(0, 1);

    public static void main(String[] args) {
        Thread main = new Thread(() -> {
            int stamp = count.getStamp(); //获取当前版本

            log.info("线程{} 当前版本{}", Thread.currentThread(), stamp);
            try {
                Thread.sleep(1000); //等待1秒 ，以便让干扰线程执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean isCASSuccess = count.compareAndSet(10, 12, stamp, stamp + 1);  //此时expectedReference未发生改变，但是stamp已经被修改了,所以CAS失败
            log.info("CAS是否成功={}", isCASSuccess);
        }, "主操作线程");

        Thread other = new Thread(() -> {
            int stamp = count.getStamp(); //获取当前版本
            log.info("线程{} 当前版本{}", Thread.currentThread(), stamp);
            count.compareAndSet(10, 12, stamp, stamp + 1);
            log.info("线程{} 增加后版本{}", Thread.currentThread(), count.getStamp());

            // 模拟ABA问题 先更新成12 又更新回10
            int stamp1 = count.getStamp(); //获取当前版本
            count.compareAndSet(12, 10, stamp1, stamp1 + 1);
            log.info("线程{} 减少后版本{}", Thread.currentThread(), count.getStamp());
        }, "干扰线程");

        main.start();
        other.start();
    }
}
