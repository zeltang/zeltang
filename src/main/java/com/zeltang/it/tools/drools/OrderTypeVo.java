package com.zeltang.it.tools.drools;

public enum OrderTypeVo {

    // 类型枚举
    LOYAL, NEW, DISSATISFIED;

    public String getValue() {
        return this.toString();
    }

}
