package com.zeltang.it.tools.factoryPattern;

public class DoSomethingsFilter extends  BaseTestFilter{
    @Override
    public Boolean doFilter(TestContextVo contextVo) {
        TestVo testVo = contextVo.getTestVo();

        // 判断是否需要过滤，可以走抽象类方法
        if (true) {
            // do something
            testMethod();
        }
        return false;
    }
}
