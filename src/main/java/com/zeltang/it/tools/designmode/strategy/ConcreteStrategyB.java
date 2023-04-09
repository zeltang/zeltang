package com.zeltang.it.tools.designmode.strategy;

/**
 * 2.定义具体策略角色
 */
public class ConcreteStrategyB implements Strategy {
    @Override
    public void show() {
        System.out.println("B");
    }
}
