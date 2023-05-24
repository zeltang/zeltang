package com.zeltang.it.tools.ifelse;

import java.util.function.Function;

public interface TestService {
    Function<String, Object> handleTyep1(String order);
    Function<String, Object> handleTyep2(String order);
    Function<String, Object> handleTyep3(String order);
    Function<String, Object> handleTyep4(String order);
    Function<String, Object> handleTyep5(String order);
    Function<String, Object> handleTyep6(String order);
    Function<String, Object> handleTyep7(String order);
    Function<String, Object> handleTyep8(String order);
}
