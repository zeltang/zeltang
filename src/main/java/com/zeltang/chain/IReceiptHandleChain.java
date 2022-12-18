package com.zeltang.chain;

import com.zeltang.chain.entity.ReceiptVo;

/**
 * 责任链接口
 */
public interface IReceiptHandleChain {
    void handleReceiptChain (ReceiptVo vo);
}
