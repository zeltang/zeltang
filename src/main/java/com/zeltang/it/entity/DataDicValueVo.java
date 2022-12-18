package com.zeltang.it.entity;

import java.io.Serializable;

public class DataDicValueVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    00A 有效<br />00X 失效<br />00U 归档
    */
    private String status;
    /**
    */
    private String dicValue;
    /**
    */
    private Long priority;
    /**
    */
    private String dicValueName;

    private String property;

    private String dicValueDesc;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getDicValueName() {
        return dicValueName;
    }

    public void setDicValueName(String dicValueName) {
        this.dicValueName = dicValueName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDicValueDesc() {
        return dicValueDesc;
    }

    public void setDicValueDesc(String dicValueDesc) {
        this.dicValueDesc = dicValueDesc;
    }
}
