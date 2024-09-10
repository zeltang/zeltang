package com.zeltang.it.tools.thread.threadlocal;

public class ThreadLocalDemo2 {

    private static ThreadLocal<Integer> countValue = new ThreadLocal<Integer>(){
        // 实现initialValue()
        public Integer initialValue() {
            return 0;
        }
    };

    public int nextSeq(){
        countValue.set(countValue.get()  + 1);
        return countValue.get();
    }

    /**
     * 通过设置一个countValue变量，然后每个线程绑定该变量，同时进行值更新操作，测试证明各个线程互相不影响
     * @param args
     */
    public static void main(String[] args){
        ThreadLocalDemo2 demo = new ThreadLocalDemo2();
        MyThread thread1 = new MyThread(demo);
        MyThread thread2 = new MyThread(demo);
        MyThread thread3 = new MyThread(demo);
        MyThread thread4 = new MyThread(demo);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    private static class MyThread extends Thread{
        private ThreadLocalDemo2 demo;

        MyThread(ThreadLocalDemo2 demo){
            this.demo = demo;
        }

        @Override
        public void run() {
            for(int i = 0 ; i < 3 ; i++){
                System.out.println(Thread.currentThread().getName() + " countValue :" + demo.nextSeq());
            }
        }
    }
}
