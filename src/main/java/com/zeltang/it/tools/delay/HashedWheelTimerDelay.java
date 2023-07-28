package com.zeltang.it.tools.delay;

import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * 时间轮算法
 */
public class HashedWheelTimerDelay {
    static class MyTimerTask implements TimerTask {
        boolean flag;

        public MyTimerTask (boolean flag) {
            this.flag=flag;
        }

        public void run (Timeout timeout) throws Exception {
            // TODO Auto-generatedmethodstub
            System.out.println("要去数据库删除订单了。。。。");
            this.flag = false;
        }
    }

    public static void main (String[]argv) {
        MyTimerTask timerTask = new MyTimerTask(true);
        Timer timer = (Timer) new HashedWheelTimerDelay();
        timer.newTimeout(timerTask,5, TimeUnit.SECONDS);
        int i = 1;
        while(timerTask.flag) {
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generatedcatchblock
                e.printStackTrace();
            }
            System.out.println(i+"秒过去了"); i++;
        }
    }
}
