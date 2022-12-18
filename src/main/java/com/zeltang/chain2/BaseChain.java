package com.zeltang.chain2;


import com.zeltang.chain.entity.ReceiptVo;

public abstract class BaseChain {
    protected BaseChain next;

    public void setNext (BaseChain next) {
        this.next = next;
    }

    public void handleRequest (ReceiptVo vo) {
        if (isMyResponsibility(vo)) {
            this.handle(vo);
        } else if (this.next != null) {
            this.next.handleRequest(vo);
        } else {
            System.out.println("没有能处理这个消息的处理器");
        }
    }

    public abstract void handle(ReceiptVo vo);

    public abstract boolean isMyResponsibility(ReceiptVo vo);

    public BaseChain getNext() {
        return next;
    }

}
