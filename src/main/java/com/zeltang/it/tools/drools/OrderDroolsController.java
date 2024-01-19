package com.zeltang.it.tools.drools;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 网上购物
 * 根据不同的规则计算折扣，例如：
 * 1.VIP客户增加折扣
 * 2.购买超过一定额度增加折扣
 * 3....
 * 且这些规则也可能会发生变化，或者增加新的规则
 */
@RestController
@RequestMapping("/drools")
public class OrderDroolsController {

    @Resource
    private IOrderDiscountService orderDiscountService;

    @RequestMapping("/getDiscount")
    public OrderDiscountVo getDiscount(@RequestBody OrderRequestVo requestVo) {
        return orderDiscountService.getDiscount(requestVo);
    }

}
