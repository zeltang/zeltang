package com.zeltang.it.tools.drools;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderDiscountServiceImpl implements IOrderDiscountService{

    @Resource
    private KieContainer kieContainer;

    @Override
    public OrderDiscountVo getDiscount (OrderRequestVo requestVo) {
        OrderDiscountVo orderDiscountVo = new OrderDiscountVo();
        // 开启会话
        KieSession kieSession = kieContainer.newKieSession();
        // 设置折扣对象
        kieSession.setGlobal("orderDiscountVo", orderDiscountVo);
        // 设置订单对象
        kieSession.insert(requestVo);
        // 触发规则
        kieSession.fireAllRules();
        // 终止会话
        kieSession.dispose();
        return orderDiscountVo;
    }
}
