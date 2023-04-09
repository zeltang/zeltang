package com.zeltang.it.tools.idempotent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tinygroup.springutil.SpringUtil;

@RequestMapping("/idempotence")
public class IdempotenceController {
    /**
     * 生成幂等号
     * @return
     */
    @GetMapping("/generateId")
    @IdempotenceRequired
    public String generateId(){
        IdempotenceUtil idempotenceUtil= SpringUtil.getBean(IdempotenceUtil.class);
        String uId=idempotenceUtil.generateId();
        return uId;
    }
}
