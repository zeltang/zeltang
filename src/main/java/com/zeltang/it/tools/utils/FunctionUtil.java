package com.zeltang.it.tools.utils;

import com.zeltang.it.tools.functionalInterface.BranchHandler;
import com.zeltang.it.tools.functionalInterface.PresentOrElseHandler;
import com.zeltang.it.tools.functionalInterface.ThrowExceptionFunction;

public class FunctionUtil {

    public static ThrowExceptionFunction isTrue(boolean b) {
        return (message) -> {
            if (!b) {
                throw new RuntimeException(message);
            }
        };
    }

    public static BranchHandler isTrueOrFalse (boolean b) {
        return (trueHandler, falseHandler) -> {
            if (b) {
                trueHandler.run();
            } else {
                falseHandler.run();
            }
        };
    }

    public static PresentOrElseHandler<?> isPresent (String obj) {
        return (action, falseHandler) -> {
            if (obj != null) {
                action.accept(obj);
            } else {
                falseHandler.run();
            }
        };
    }

}
