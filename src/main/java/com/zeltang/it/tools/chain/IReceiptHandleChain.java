package com.zeltang.it.tools.chain;

import com.zeltang.it.tools.chain.entity.ReceiptVo;

/**
 * 责任链接口
 */
public interface IReceiptHandleChain {
    void handleReceiptChain (ReceiptVo vo);
}
