package com.zeltang.chain2;

import com.zeltang.chain.entity.ReceiptVo;

public class DefaultHandler implements Basehandler{
    @Override
    public void handle(ReceiptVo vo) {
        // 使用责任链模式对消息进行处理，此责任链模式使用的比较简单
        DefaultChain defaultChain = new DefaultChain();

        Test1Chain chain1 = new Test1Chain();
        defaultChain.setNext(chain1);

        Test2Chain chain2 = new Test2Chain();
        chain1.setNext(chain2);

        chain2.setNext(null);

        defaultChain.handleRequest(vo);
    }
}
