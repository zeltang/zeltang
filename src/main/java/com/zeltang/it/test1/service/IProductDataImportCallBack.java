package com.zeltang.it.test1.service;

import java.util.List;

public interface IProductDataImportCallBack<T> {
    Class<?> getImportClass();

    String checkExcelTitleValue(T o) throws Exception;

    String updateProductData(T o) throws Exception;

    String checkDuplicationData (T o, List<BatchImportInvalidMsg> list) throws Exception;

    default String sendEmail (List<BatchImportInvalidMsg> list) throws Exception {return null;};
}
