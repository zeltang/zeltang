package com.zeltang.it.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Person {
    private String id;

    //姓名
    private String name;

    //年龄
    private Integer age;

    //出生日期, 日期格式必须和ES中这个索引字段的日期格式保持一致
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

}
