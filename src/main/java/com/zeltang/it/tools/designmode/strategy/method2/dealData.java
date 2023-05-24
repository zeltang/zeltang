package com.zeltang.it.tools.designmode.strategy.method2;

import com.zeltang.it.tools.designmode.strategy.Strategy;
import com.zeltang.it.utils.SpringUtil;

public class dealData {
    public void dealData(StrategyVo strategyVo){
        if(strategyVo == null){
            return;
        }
        StrategyEnum strategyEnum = StrategyEnum.valueOf(strategyVo.getType());
        if(strategyEnum == null){
            //没有在枚举中匹配到处理器，说明业务参数不合法或者没有添加对应的业务枚举
            return;
        }
        Strategy strategy = SpringUtil.getBean(strategyEnum.getHandler(), Strategy.class);
        if(strategy == null){
            //没有从spring容器中获取到对应处理器到实例，属于异常情况，检查枚举配置和处理器是否正确注入spring容器
            return;
        }
        //交给对应到处理器去处理
        strategy.show();
        //处理完成
    }
}
