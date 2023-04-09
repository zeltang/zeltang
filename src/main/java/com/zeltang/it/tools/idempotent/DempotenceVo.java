package com.zeltang.it.tools.idempotent;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("people")
public class DempotenceVo {
    @Id
    String id;
}
