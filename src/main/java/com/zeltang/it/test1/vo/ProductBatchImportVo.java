package com.zeltang.it.test1.vo;

import com.zeltang.it.test1.service.BatchImportInvalidMsg;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName
 * @Author tangzelong
 * @Date 2020/8/3 10:34
 * @Version 1.0
 */
@Data
public class ProductBatchImportVo {

    private String exportEmailTitle;

    private String email;

    private List<ProductExcelCodeTitleVo> titleVos;

    private List<?> valueVos;

    private List<BatchImportInvalidMsg> errList = new ArrayList<>();
    private List<BatchImportInvalidMsg> successList = new ArrayList<>();


    @Data
    public static class ProductExcelCodeTitleVo {
        private String title;
        private String name;
        private String code;
    }

    @Data
    public static class ProductExcelValueVo implements BatchImportInvalidMsg {
        private String mfrPartNumber;
        private String brandName;
        private String purDelivery;
        private String purDeliveryHk;
        private String sunlordDelivery;
        private String sunlordDeliveryHk;
        private String miniPkg;
        private String miniQty;
        private String supplyMultiple;
        private String mpnLevel;
        private String changeTypeParam1;
        private String changeTypeParam2;
        private String changeType;
        private String autoPortion;
        private String portionRemark;
        private String specDesc;
        private String isOrders;
        private String customsMfrName;
        private String hsCode;
        private String tariff;
        private String is3c;
        private String productLineName;
        private String shelfLife;
        private String customsMfrEnName;
        private String mslLevel;
        private String isHf;
        private String isRohs;
        private String productCatalogCode;
        private String productCategory;
        private String supplyStatus;
        private String errMsg;
        private String isCarLevel;
        private String isShortage;
        private String nexperiaMag;
        private String isQmAck;
    }

    @Data
    public static class BrandExcelValueVo implements BatchImportInvalidMsg {
        private String brandCode;
        private String brandName;
        private String invoiceUseType;
        private String usExportControl;
        private String brandNameCn;
        private String brandDesc;
        private String errMsg;
    }

    @Data
    public static class TestExcelValueVo implements BatchImportInvalidMsg {
        private String mfrPartNumber;
        private String brandName;
        private String test;
        private String errMsg;
    }

}
