package com.zeltang.it.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @ClassName TestListener
 * @Author tangzelong
 * @Date 2020/12/29 11:04
 * @Version 1.0
 */
@Slf4j
//@WebListener
public class TestListener implements HttpSessionListener, HttpSessionAttributeListener {

    public TestListener () {
        log.info("listener初始化。。。。。。。。。。");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("session123。。。。");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("session345。。。。");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("session567。。。。");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("session创建。。。。");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("session销毁。。。。");
    }
}
