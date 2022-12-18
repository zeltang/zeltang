package com.zeltang.chain;

import java.util.*;

/**
 * 处理者容器
 */
public class ReceiptHandlerContainer {

    private ReceiptHandlerContainer () {
    }

    public static List<IReceiptHandler> getReceiptHandlerList () {
        List<IReceiptHandler> res = new ArrayList<>();
        // 获取IReceiptHandler接口的实现类
        Set<Class<?>> classSetBySuper = ReflectionUtil.getClassSetBySuper(IReceiptHandler.class);
        Iterator<Class<?>> iterator = classSetBySuper.iterator();
        while (iterator.hasNext()) {
            Class<?> cls = iterator.next();
            try {
                res.add((IReceiptHandler) cls.newInstance());
            } catch (Exception e) {
                // TODO
            }
        }
        return res;
    }

}
