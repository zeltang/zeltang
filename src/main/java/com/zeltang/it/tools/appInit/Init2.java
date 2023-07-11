package com.zeltang.it.tools.appInit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Init2 implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("【实现InitializingBean接口初始化数据......】");
    }
}
