package com.zeltang.it.tools.limitFlow.bucket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 将拦截器注入
 */
@Configuration
public class BucketWebMvcConfug implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //令牌桶拦截器添加拦截器并选择拦截路径
        registry.addInterceptor (bucketInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public BucketInterceptor bucketInterceptor(){
        return new BucketInterceptor();
    }
}
