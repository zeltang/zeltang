package com.zeltang.it.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TestEntity
 * @Author tangzelong
 * @Date 2020/1/14 10:13
 * @Version 1.0
 */
public class TestEntity {

    private Map<String, Long> testMap = new HashMap<>();

    private String code;

    private boolean bol;

    private Long num;

    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isBol() {
        return bol;
    }

    public void setBol(boolean bol) {
        this.bol = bol;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
