package com.zeltang.it;

import com.zeltang.it.tools.limitFlow.bucket.BucketUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@MapperScan("com.zeltang.it.mapper.*")
//@EnableTransactionManagement
@SpringBootApplication
//@ServletComponentScan
public class ItApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItApplication.class, args);
        // 令牌桶：初始化并生成定时器
        // 为了方便测试这里定义1容量1增长速率
        BucketUtil bucketUtil = new BucketUtil(1,1);
        // 生成名为：bucket的令牌桶
        BucketUtil.buckets.put("bucket",bucketUtil);
    }

    @Scheduled(fixedRate=1000)
    //定时1s
    public void timer(){
        if (BucketUtil.buckets.containsKey("bucket")) {
            //名为：bucket的令牌桶开始不断生成令牌
            BucketUtil.buckets.get("bucket").incrTokens();
        }
    }

}
