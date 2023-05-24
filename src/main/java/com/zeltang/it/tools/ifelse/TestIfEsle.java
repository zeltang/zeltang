package com.zeltang.it.tools.ifelse;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TestIfEsle {
    /**
     * 传统的 if else 解决方法
     * 当每个业务逻辑有 3 4 行时，用传统的策略模式不值得，直接的if else又显得不易读
     */
    public String doBizService(String order) {
        if ("type1".equals(order)) {
            return "执行业务逻辑1";
        } else if ("type2".equals(order)) {
            return "执行业务逻辑2";
        }else if ("type3".equals(order)) {
            return "执行业务逻辑3";
        }else if ("type4".equals(order)) {
            return "执行业务逻辑4";
        }else if ("type5".equals(order)) {
            return "执行业务逻辑5";
        }else if ("type6".equals(order)) {
            return "执行业务逻辑6";
        }else if ("type7".equals(order)) {
            return "执行业务逻辑7";
        }else if ("type8".equals(order)) {
            return "执行业务逻辑8";
        }else if ("type9".equals(order)) {
            return "执行业务逻辑9";
        }
        return "不在处理的逻辑中返回业务错误";
    }

    //---------------------------高逼格分割线------------------------------------

    @Resource
    private TestService testService;


    /**
     * 业务逻辑分派Map
     * Function为函数式接口，
     * 下面代码中 Function<String, Object> 的含义是接收一个String类型的变量用来获取你要执行哪个Function，实际使用中可自行定义
     * Function执行完成返回一个Object类型的结果,这个结果就是统一的业务处理返回结果，实际使用中可自行定义
     */
    private static Map<String, Function<String, Object>> checkResultDispatcher = new HashMap<>();

    /**
     * 初始化 业务逻辑分派Map 其中value 存放的是 lambda表达式
     * 也可以依赖于spring的@PostConstruct 初始化checkResultDispatcher 根据各个技术栈调整
     */
    {
        checkResultDispatcher.put("type1", order -> testService.handleTyep1(order));
        checkResultDispatcher.put("type2", order -> testService.handleTyep2(order));
        checkResultDispatcher.put("type3", order -> testService.handleTyep3(order));
        checkResultDispatcher.put("type4", order -> testService.handleTyep4(order));
        checkResultDispatcher.put("type5", order -> testService.handleTyep5(order));
        checkResultDispatcher.put("type6", order -> testService.handleTyep6(order));
        checkResultDispatcher.put("type7", order -> testService.handleTyep7(order));
        checkResultDispatcher.put("type8", order -> testService.handleTyep8(order));
    }

    public Object handleBizService(String type) {
        //从逻辑分派Dispatcher中获得业务逻辑代码，result变量是一段lambda表达式
        Function<String, Object> result = checkResultDispatcher.get(type);
        if (result != null) {
            //执行这段表达式获得String类型的结果
            return result.apply(type);
        }
        return "不在处理的逻辑中返回业务错误";
    }
}
