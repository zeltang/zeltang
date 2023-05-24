package com.zeltang.it.tools.designmode.strategy.method2;

/**
 * 每一个Strategy的实现类，就相当于一个if else中的处理逻辑执行器
 * 然后定义一个枚举，把这些逻辑执行器管理起来：
 */
public enum StrategyEnum {
    A_DO("A", "ConcreteStrategyA"),
    B_DO("B", "ConcreteStrategyB");

    /**
     * 业务type
     */
    private String type;

    /**
     * 业务描述
     */
    private String name;

    StrategyEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
    }

    /**
     * 对应处理器
     */
    private String handler;

    public static StrategyEnum getEnum(String type) {
        if (type == null) {
            return null;
        }
        for (StrategyEnum strategyEnum : values()) {
            if (type.equals(strategyEnum.getType())) {
                return strategyEnum;
            }
        }
        return null;
    }

    public String getHandler () {
        return handler;
    }
}
