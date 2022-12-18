package com.zeltang.lambda;

import lombok.Data;

import java.util.Date;

@Data
public class LambdaVo {
    private int num;

    private String code;

    private Date date;

    public LambdaVo (int num, String code) {
        this.num = num;
        this.code = code;
    }
}
