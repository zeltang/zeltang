package com.zeltang.sync;

import org.openjdk.jol.info.ClassLayout;

public class SyncTest {

    private static Object object = new Object();

    /**打印出被锁的对象*/
    private static void print() throws InterruptedException {
        synchronized (object) {
            System.out.println("####################################");
            Thread.sleep(1000);//为了加剧线程间的竞争
            System.out.println(ClassLayout.parseInstance(object).toPrintable());//打印对象被锁之后布局
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //调用synchronized之前
        System.out.println(ClassLayout.parseInstance(object).toPrintable());//打印对象锁之前的布局
        //调用synchronized之后，单个线程没有竞争
        print();
        //有若干个线程竞争情况下
        for (int i = 0; i < 1; ++i){
            new Thread(() -> {
                try {
                    print();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


}
