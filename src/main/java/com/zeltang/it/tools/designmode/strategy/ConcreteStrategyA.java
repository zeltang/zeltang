package com.zeltang.it.tools.designmode.strategy;

import org.springframework.stereotype.Service;

/**
 * 2.定义具体策略角色
 */
@Service("ConcreteStrategyA")
public class ConcreteStrategyA implements Strategy {
    @Override
    public void show() {
        System.out.println("A");
    }
}
