package com.zeltang.it.utils;

import java.util.Collection;

public class Utils {

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.size() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean notEmpty(Object arg) {
        return !isEmpty(arg);
    }

}
