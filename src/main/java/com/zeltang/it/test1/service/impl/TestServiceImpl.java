package com.zeltang.it.test1.service.impl;

import com.zeltang.it.test1.service.BatchImportInvalidMsg;
import com.zeltang.it.test1.service.IProductDataImportCallBack;
import com.zeltang.it.test1.service.ITestService;
import com.zeltang.it.test1.vo.ProductBatchImportVo;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class TestServiceImpl extends ProductImportServiceImpl implements ITestService {
    @Override
    public String productDataBatchImport(ProductBatchImportVo vo) throws Exception {
        return super.productDataBatchImport(vo, new IProductDataImportCallBack<ProductBatchImportVo.ProductExcelValueVo>() {

            @Override
            public Class<?> getImportClass() {
                return ProductBatchImportVo.ProductExcelValueVo.class;
            }

            @Override
            public String checkExcelTitleValue(ProductBatchImportVo.ProductExcelValueVo o) throws Exception {
//                ProductBatchImportVo.ProductExcelValueVo valueVo = JSONObject.parseObject(JSONObject.toJSONString(o), ProductBatchImportVo.ProductExcelValueVo.class);
//                valueVo.setAutoPortion("T");
//
                try {
                    Class<?> aClass = o.getClass();
                    Field declaredField = aClass.getDeclaredField("changeType");
                    declaredField.setAccessible(true);
                    declaredField.set(o, "SSS");

                } catch (Exception e) {

                }
//                o.setChangeType("TTT");


                return null;
            }

            @Override
            public String updateProductData(ProductBatchImportVo.ProductExcelValueVo valueVo) throws Exception {
                return null;
            }

            @Override
            public String checkDuplicationData(ProductBatchImportVo.ProductExcelValueVo o, List<BatchImportInvalidMsg> list) throws Exception {
                return null;
            }
        });



    }
}
