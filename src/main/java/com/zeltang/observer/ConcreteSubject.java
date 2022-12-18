package com.zeltang.observer;

/**
 * 具体被观察者
 */
public class ConcreteSubject extends AbstractSubject {
    // 被观察者发送消息
    public void sendMsg(String context) {
        System.out.println("具体被观察者角色发送消息:"+context);
        super.notifyObserver(context);
    }
}
