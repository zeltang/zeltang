package com.zeltang.it.tools.appInit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Init3 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("【实现CommandLineRunner接口初始化数据......】");
    }
}
