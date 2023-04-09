package com.zeltang.it.tools.observer;

/**
 * 抽象观察者角色
 */
public abstract class AbstractObserver {
    /**
     * 接收消息
     * @param context
     */
    public abstract void receiveMsg(String context);
}
