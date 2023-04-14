package com.zeltang.it.tools.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 定义一个后置处理器，用来识别自定义注解并进行对应的功能处理
 */
public class MyAnnotationProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(MyCustomAnnotation.class)) {
            // do something：这里可以写具体的业务逻辑
        }
        return bean;
    }
}
