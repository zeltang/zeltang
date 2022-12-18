package com.zeltang.chain2;

import com.zeltang.chain.entity.ReceiptVo;
import org.apache.commons.codec.binary.StringUtils;

public class Test2Chain extends BaseChain {

    @Override
    public void handle(ReceiptVo vo) {
        // TODO
        System.out.println("TEST2......");
    }

    @Override
    public boolean isMyResponsibility(ReceiptVo vo) {
        if (vo != null) {
            return StringUtils.equals("TEST2", vo.getType());
        }
        return false;
    }
}
