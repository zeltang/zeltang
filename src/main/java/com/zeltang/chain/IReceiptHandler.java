package com.zeltang.chain;

/**
 * 抽象回执处理者接口
 */
public interface IReceiptHandler<ReceiptVo> {

    void handleReceipt (ReceiptVo vo);

    Boolean isMyResponsibility (ReceiptVo vo);



}
