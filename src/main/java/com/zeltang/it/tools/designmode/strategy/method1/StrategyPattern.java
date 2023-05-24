package com.zeltang.it.tools.designmode.strategy.method1;

import com.zeltang.it.tools.designmode.strategy.ConcreteStrategyA;
import com.zeltang.it.tools.designmode.strategy.ConcreteStrategyB;
import com.zeltang.it.tools.designmode.strategy.Strategy;
import com.zeltang.it.tools.designmode.strategy.method1.ContextPlatform;

/**
 * 4.客户端调用
 */
public class StrategyPattern {

    public static void main(String[] args){
        ContextPlatform context;

        String time1 = "9月";
        Strategy strategyB = new ConcreteStrategyB();
        context = new ContextPlatform(strategyB);
        context.platformShow(time1);

        String time3 = "6月";
        Strategy strategyA = new ConcreteStrategyA();
        context = new ContextPlatform(strategyA);
        context.platformShow(time3);
    }
}
