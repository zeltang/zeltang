package com.zeltang.it.tools.thread.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.IntStream;

public class ThreadLocalDemo {
    static ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();

    private static void test01 () {
        IntStream.range(0, 5).forEach(s->{
            new Thread(()->{
                integerThreadLocal.set(s);
                System.out.println(Thread.currentThread().getName()
                        + "," + integerThreadLocal.get());
            }).start();
        });
    }

    static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static void test02 () {
        IntStream.range(0, 5).forEach(i -> {
            // 创建5个线程，分别从threadLocal取出SimpleDateFormat，然后格式化日期
            new Thread(() -> {
                try {
                    System.out.println(simpleDateFormatThreadLocal.get().parse("2022-11-11 00:00:00"));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
    }

    //创建可被子线程继承数据的ThreadLocal，实现父子线程共享ThreadLocal数据
    static ThreadLocal<String> stringThreadLocal = new InheritableThreadLocal<>();

    private static void test03 () {
        stringThreadLocal.set("AAAAAA");
        // 启动一个子线程，看是否能获取到主线程数据
        new Thread(() -> {
            System.out.println(stringThreadLocal.get()); // 输出 关注公众号:一灯架构
        }).start();
    }

    public static void main(String[] args) {
        test03();
    }
}
