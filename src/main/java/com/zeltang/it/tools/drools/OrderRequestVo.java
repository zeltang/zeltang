package com.zeltang.it.tools.drools;

import lombok.Data;

@Data
public class OrderRequestVo {
    private String orderNo;

    private Integer age;

    private OrderTypeVo orderType;

    private String amount;

}
