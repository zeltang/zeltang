package com.zeltang.chain.handler;

import com.zeltang.chain.IReceiptHandler;
import com.zeltang.chain.entity.ReceiptVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

@Slf4j
public class Test2Handler implements IReceiptHandler<ReceiptVo> {

    @Override
    public void handleReceipt(ReceiptVo vo) {
        System.out.println("TEST2------");
    }

    @Override
    public Boolean isMyResponsibility(ReceiptVo vo) {
        System.out.println("enter 2");
        if (vo != null) {
            return StringUtils.equals("TEST2", vo.getCode());
        }
        return false;
    }
}
