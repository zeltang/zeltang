package com.zeltang.it.tools.appInit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class Init1 {

    /**
     * 程序启动时，初始化数据
     *
     * @PostConstruct 用来修饰一个非静态的void()方法，当Bean创建完成是，会仅且执行一次
     * 例如：加载数据字典、缓存 到redis中去
     */
    @PostConstruct
    public void init() {
        log.info("【@PostConstruct初始化数据......】");
    }
}
