package com.zeltang.it.tools.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义一个配置类，注入后置处理器
 */
@Configuration
public class AnnotationConfig {

    @Bean
    public static MyAnnotationProcessor myAnnotationProcessor() {
        return new MyAnnotationProcessor();
    }
}
