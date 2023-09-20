package com.zeltang.it.tools.limitFlow.bucket;

/**
 * 自定义异常
 */
public class BucketException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String msg;

    public BucketException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
