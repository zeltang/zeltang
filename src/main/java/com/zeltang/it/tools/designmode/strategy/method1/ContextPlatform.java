package com.zeltang.it.tools.designmode.strategy.method1;

import com.zeltang.it.tools.designmode.strategy.Strategy;

/**
 * 3.定义环境角色（Context）
 */
public class ContextPlatform {
    //持有抽象策略的引用
    private Strategy myStrategy;
    //生成构造方法，根据传入的策略参数选择策略
    public ContextPlatform(Strategy strategyType) {
        this.myStrategy = strategyType;
    }

    public void platformShow(String time) {
        myStrategy.show();
    }
}
