package com.zeltang.it.tools.delay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * JDK的延迟队列
 */
public class JdkDelay implements Delayed {
    private String orderId;
    private long timeout;
    JdkDelay(String orderId, long timeout) {
        this.orderId = orderId;
        this.timeout = timeout + System.nanoTime();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(timeout-System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        if (other==this) {
            return 0;
        }
        JdkDelay t = (JdkDelay)other;
        long d=(getDelay(TimeUnit.NANOSECONDS)-t .getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    void print(){
        System.out.println(orderId+"编号的订单要删除啦。。。。");
    }

    public static void main(String[]args){
        // TODO Auto-generated methodstub
        List<String> list= new ArrayList<String>();
        list.add("00000001");
        list.add("00000002");
        list.add("00000003");
        list.add("00000004");
        list.add("00000005");
        DelayQueue<JdkDelay> queue = new DelayQueue<JdkDelay>();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 5; i++){
            //延迟三秒取出
            queue.put(new JdkDelay(list.get(i), TimeUnit.NANOSECONDS.convert(3,TimeUnit.SECONDS)));
            try{
                queue.take().print();
                System.out.println("After"+ (System.currentTimeMillis()-start)+"MilliSeconds");
            } catch (InterruptedException e){
                // TODO Auto-generatedcatchblock
                e.printStackTrace();
            }
        }
    }
}
