package com.zeltang.it.tools.delay;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 数据库轮询
 */
public class DataBaseDelay {

    public static void main(String[]args) throws Exception {
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("job1","group1").build();
        //创建触发器每3秒钟执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group3").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever()).build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        //将任务及其触发器放入调度器
        scheduler.scheduleJob(jobDetail,trigger);
        //调度器开始调度任务
        scheduler.start();
    }
}
