package com.zeltang.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象主题（抽象被观察者角色）
 */
public abstract class AbstractSubject {
    // 持有所有抽象观察者角色的集合引用
    private final List<AbstractObserver> observers = new ArrayList<>();

    // 添加一个观察者
    public void addObserver(AbstractObserver observer) {
        observers.add(observer);
    }

    // 移除一个观察者
    public void removeObserver(AbstractObserver observer) {
        observers.remove(observer);
    }

    // 通知所有的观察者，执行观察者更新方法
    public void notifyObserver(String context) {
        observers.forEach(observer->observer.receiveMsg(context));
    }
}
