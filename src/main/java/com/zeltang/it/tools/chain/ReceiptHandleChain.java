package com.zeltang.it.tools.chain;

import com.zeltang.it.tools.chain.entity.ReceiptVo;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 责任链实现类
 */
public class ReceiptHandleChain implements IReceiptHandleChain {

    // 记录当前处理位置
    private int index = 0;

    // 处理者集合
    private static List<IReceiptHandler> handlerList;

    // 从容器中获取处理器对象
    static {
        handlerList = ReceiptHandlerContainer.getReceiptHandlerList();
    }

    @Override
    public void handleReceiptChain(ReceiptVo vo) {
        if (CollectionUtils.isEmpty(handlerList)) {
            return;
        }
        if (index != handlerList.size()) {
            IReceiptHandler receiptHandler = handlerList.get(index++);
            if (receiptHandler.isMyResponsibility(vo)) {
                receiptHandler.handleReceipt(vo);
                return;
            }
            // 不满足，继续往下寻找处理器
            this.handleReceiptChain(vo);
        }
//        for (IReceiptHandler handler : handlerList) {
//            if (handler.isMyResponsibility(vo)) {
//                handler.handleReceipt(vo);
//                break;
//            }
//        }
    }
}
