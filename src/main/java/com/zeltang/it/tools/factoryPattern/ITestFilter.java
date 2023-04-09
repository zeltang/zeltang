package com.zeltang.it.tools.factoryPattern;

public interface ITestFilter {
    Boolean doFilter (TestContextVo contextVo);

    // 可以拓展一些其他方法
}
