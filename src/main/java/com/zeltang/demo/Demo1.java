package com.zeltang.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo1 {
    static int value = 0;
    static AtomicInteger atomicValue = new AtomicInteger(0);

    public static void main(String[] args) {
        synchronizedAdd();
//        atomicAdd();
    }

    @SuppressWarnings("unused")
    private static void synchronizedAdd() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (Demo1.class) {
                    System.out.println(++Demo1.value);
                }
            }).start();
        }
    }

    private static void atomicAdd() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(Demo1.atomicValue.incrementAndGet())).start();
        }
    }
}
