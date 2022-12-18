package com.zeltang.chain2;

import com.zeltang.chain.entity.ReceiptVo;
import org.apache.commons.codec.binary.StringUtils;

public class Test1Chain extends BaseChain {

    @Override
    public void handle(ReceiptVo vo) {
        // TODO
        System.out.println("TEST1...");
    }

    @Override
    public boolean isMyResponsibility(ReceiptVo vo) {
        if (vo != null) {
            return StringUtils.equals("TEST1", vo.getType());
        }
        return false;
    }
}
