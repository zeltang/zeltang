package com.zeltang.it.tools.chain.handler;

import com.zeltang.it.tools.chain.IReceiptHandler;
import com.zeltang.it.tools.chain.entity.ReceiptVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

@Slf4j
public class Test1Handler implements IReceiptHandler<ReceiptVo> {

    @Override
    public void handleReceipt(ReceiptVo vo) {
        System.out.println("TEST1______");
    }

    @Override
    public Boolean isMyResponsibility(ReceiptVo vo) {
        System.out.println("enter 1");
        if (vo != null) {
            return StringUtils.equals("TEST1", vo.getCode());
        }
        return false;
    }
}
