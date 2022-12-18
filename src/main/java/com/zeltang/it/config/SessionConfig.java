package com.zeltang.it.config;

import com.zeltang.it.controller.TestListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.util.IntrospectorCleanupListener;

import java.util.EventListener;

/**
 * @ClassName SessionConfig
 * @Author tangzelong
 * @Date 2020/12/29 19:28
 * @Version 1.0
 */
@Slf4j
@Configuration
public class SessionConfig {
    @Bean
    public ServletListenerRegistrationBean<EventListener> servletListenerRegistrationBean() {
        log.info("注册。。。。。。。。。。。");
        ServletListenerRegistrationBean<EventListener> srb = new ServletListenerRegistrationBean<EventListener>();
        //防止Spring内存溢出监听器
        srb.setListener(new IntrospectorCleanupListener());
        //request监听器 主要需要配置这个监听器
        srb.setListener(new RequestContextListener());
        srb.setListener(new TestListener());
        return srb;
    }
}
