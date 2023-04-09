package com.zeltang.it.tools.observer;

public class ObserverPatternTest {
    public static void main(String[]args) {
        ConcreteSubject subject = new ConcreteSubject();
        subject.addObserver(new ConcreteObserver());
        subject.sendMsg("HelloWorld!");
    }
}
