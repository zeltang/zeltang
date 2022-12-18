package com.zeltang.chain2;

import com.zeltang.chain.entity.ReceiptVo;

public class DefaultChain extends BaseChain {
    @Override
    public void handle(ReceiptVo vo) {
        System.out.println("默认的责任链什么也不干,回传消息");
    }

    @Override
    public boolean isMyResponsibility(ReceiptVo vo) {
        return false;
    }
}
