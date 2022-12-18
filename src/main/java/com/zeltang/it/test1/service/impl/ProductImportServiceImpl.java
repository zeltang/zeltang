package com.zeltang.it.test1.service.impl;

import com.alibaba.fastjson.JSON;
import com.zeltang.it.test1.service.IProductDataImportCallBack;
import com.zeltang.it.utils.Utils;
import com.zeltang.it.test1.service.BatchImportInvalidMsg;
import com.zeltang.it.test1.vo.ProductBatchImportVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName IProductImportServiceImpl
 * @Author tangzelong
 * @Date 2020/7/31 13:42
 * @Version 1.0
 */
@Slf4j
public abstract class ProductImportServiceImpl {

    public String productDataBatchImport(ProductBatchImportVo vo, IProductDataImportCallBack callBack) throws Exception {
        log.info("开始执行批量导入:{}", JSON.toJSONString(vo));
        // 数据集非空判断
        if (Utils.isEmpty(vo.getValueVos())) {
            log.info("批量导入数据集为空");
            return "";
        }
        if (Utils.isEmpty(vo.getEmail())) {
            log.info("批量导入邮件发送人不能为空");
            return "";
        }

        // 获取批量导入VO的Class，将前端传递的values数据集转换为需要处理的VO
        Class<?> importClass = callBack.getImportClass();
        if (Utils.isEmpty(importClass)) {
            log.info("批量导入数据集未指定Class");
            return "";
        }
        vo.setValueVos(JSON.parseArray(JSON.toJSONString(vo.getValueVos())).toJavaList(importClass));

        if (vo.getValueVos().isEmpty()) {
            return "待处理数据不能为空";
        }
        productBatchImport(vo, callBack);
        return "稍后请至邮箱中查看处理结果";
    }

    /**
     * 大数据批量处理
     * @param vo
     * @param callBack
     * @throws Exception
     */
    private void productBatchImport (ProductBatchImportVo vo, IProductDataImportCallBack callBack) throws Exception {
        Map<String, List<BatchImportInvalidMsg>> checkValueList = checkValue(vo, callBack);
        List<BatchImportInvalidMsg> successDataList = checkValueList.get("OPERATE_RES_SUCCESS");
        List<BatchImportInvalidMsg> errDataList = checkValueList.get("OPERATE_RES_ERROR");

        if (CollectionUtils.isEmpty(successDataList)) {
            // 发送邮件
            sendEmail(vo, errDataList, successDataList);
            return;
        }

        // 对校验正常的数据进行业务处理
        CountDownLatch countDown = new CountDownLatch(successDataList.size());
        List<BatchImportInvalidMsg> updateSuccessDataList =  Collections.synchronizedList(new ArrayList<>());
        Iterator<BatchImportInvalidMsg> succIterator = successDataList.iterator();
        while (succIterator.hasNext()) {
            BatchImportInvalidMsg next = succIterator.next();
            String res = callBack.updateProductData(next);
            if (Utils.notEmpty(res)) {
                next.setErrMsg(res);
                errDataList.add(next);
            } else {
                updateSuccessDataList.add(next);
            }
        }

        // 邮件发送成功的数据给员工
        if (CollectionUtils.isNotEmpty(updateSuccessDataList)) {
            callBack.sendEmail(updateSuccessDataList);
        }
        // 邮件发送异常数据给导入人
        sendEmail(vo, errDataList, updateSuccessDataList);
    }

    /**
     * Excel数据校验，分别返回成功和失败数据集
     * @param vo
     * @param callBack
     * @return
     * @throws Exception
     */
    private Map<String, List<BatchImportInvalidMsg>> checkValue (ProductBatchImportVo vo, IProductDataImportCallBack callBack) throws Exception {
        Map<String, List<BatchImportInvalidMsg>> map = new HashMap<>();
        List<BatchImportInvalidMsg> errDataList =  Collections.synchronizedList(new ArrayList<>());
        List<BatchImportInvalidMsg> successDataList =  Collections.synchronizedList(new ArrayList<>());
        int checkNum = 0;
        // 校验Excel的字段数据
        List<?> valueVos = vo.getValueVos();
        Iterator<?> allIterator = valueVos.iterator();
        while (allIterator.hasNext()) {
            Object next = allIterator.next();
            String errMsg = callBack.checkExcelTitleValue(next);
            if (Utils.notEmpty(errMsg)) {
                errDataList.add((BatchImportInvalidMsg)next);
                errDataList.get(checkNum).setErrMsg(errMsg);
                allIterator.remove();
                checkNum++;
            } else {
                successDataList.add((BatchImportInvalidMsg)next);
            }
        }
        // 校验重复的数据,只保留第一条数据
        Collections.reverse(successDataList);
        Iterator<?> dupIterator = successDataList.iterator();
        while (dupIterator.hasNext()) {
            Object next = dupIterator.next();
            String errMsg = callBack.checkDuplicationData(next, successDataList);
            if (Utils.notEmpty(errMsg)) {
                errDataList.add((BatchImportInvalidMsg)next);
                errDataList.get(checkNum).setErrMsg(errMsg);
                dupIterator.remove();
                checkNum++;
            }
        }
        map.put("OPERATE_RES_SUCCESS", successDataList);
        map.put("OPERATE_RES_ERROR", errDataList);

        return map;
    }

    /**
     * 将数据处理结果邮件发送
     * @param vo
     * @param errDataList
     * @throws Exception
     */
    private void sendEmail (ProductBatchImportVo vo, List<?> errDataList, List<?> successDataList) throws Exception {


    }

}
