package com.zeltang.it.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationContextUtil implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {
    private static ApplicationContext appCtx;
    private static BeanDefinitionRegistry beanDefinitionRegistry;

    public ApplicationContextUtil() {}

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return appCtx;
    }

    public static Object getBean(String beanName) {
        return appCtx.getBean(beanName);
    }

    public static <T> T getBean(Class<T> cls) {
        return appCtx.getBean(cls);
    }

    public static <T> T getBean(String beanName, Class<T> cls) {
        return appCtx.getBean(beanName, cls);
    }

    public static String getEnvProp(String key) {
        return appCtx.getEnvironment().getProperty(key);
    }

    public static void unRegisterBean(String beanName) {
        if (appCtx.containsBean(beanName)) {
            beanDefinitionRegistry.removeBeanDefinition(beanName);
        }

    }

    public static <T> T registerBean(String name, Class<T> clazz, Object... args) {
        if (appCtx.containsBean(name)) {
            Object bean = appCtx.getBean(name);
            if (bean.getClass().isAssignableFrom(clazz)) {
                return (T) bean;
            } else {
                throw new RuntimeException("BeanName 重复 " + name);
            }
        } else {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            Object[] var4 = args;
            int var5 = args.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Object arg = var4[var6];
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }

            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(name, beanDefinition);
            return appCtx.getBean(name, clazz);
        }
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> cls) {
        return appCtx.getBeansOfType(cls);
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ApplicationContextUtil.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

}
