package com.zeltang.it.controller;

import com.alibaba.excel.EasyExcel;
import com.zeltang.it.entity.ExportVo;
import com.zeltang.it.entity.TestExportVo;
import com.zeltang.it.test1.service.ITestService;
import com.zeltang.it.test1.vo.ProductBatchImportVo;
import com.zeltang.it.tools.limitFlow.bucket.BucketAnnotation;
import com.zeltang.it.utils.EasyExcelExport;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TestController
 * @Author tangzelong
 * @Date 2020/12/29 19:06
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ITestService testService;

    @BucketAnnotation
    @RequestMapping(value="/bucket")
    public String bucket() {
        return "访问成功";
    }

    /**
     * POSTMAN上传Excel解析
     * @param file
     * @throws Exception
     */
    @RequestMapping("excel")
    public void excel(@ModelAttribute MultipartFile file) throws Exception {
        Workbook workbook = null;
        String fileName = file.getOriginalFilename();
        if (fileName.endsWith("xls")) {
            POIFSFileSystem pois = new POIFSFileSystem(file.getInputStream());
            workbook = new HSSFWorkbook(pois);
        } else if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        Sheet sheet = workbook.getSheetAt(0);
        // 获取第一页总行数
        int rowCount = sheet.getPhysicalNumberOfRows();
        // 开始循环取出每一行的数据，
        for (int i = 0; i < rowCount; i++) {
//            BaseArea baseArea= new BaseArea();
            // 从第0行开始，因为第0行一般是抬头所以直接从下面一行开始
            Row row = sheet.getRow(i);
            // 取出每一列的值，从第0列开始
            String code = "";//
            String name = "";//
            Cell cellname = row.getCell(0);
            Cell cellmobile = row.getCell(1);
            cellname.setCellType(CellType.STRING);
            code = cellname.getStringCellValue().trim();
            cellmobile.setCellType(CellType.STRING);
            name = cellmobile.getStringCellValue().trim();
//            baseArea.setAreaCode(code);
//            baseArea.setAreaName(name);
        }
        log.info("123");
    }


    @RequestMapping("test1")
    public void test1() throws Exception {
        ProductBatchImportVo vo = new ProductBatchImportVo();
        List<ProductBatchImportVo.ProductExcelValueVo> valueVos = new ArrayList<>();
        ProductBatchImportVo.ProductExcelValueVo valueVo = new ProductBatchImportVo.ProductExcelValueVo();
        valueVo.setMfrPartNumber("123");
        valueVo.setBrandName("123");
        valueVos.add(valueVo);
        vo.setValueVos(valueVos);
        vo.setEmail("123");


        testService.productDataBatchImport(vo);



        log.info("123");
    }

    @RequestMapping("tenantListChange")
    public void tenantListChange(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("name", "tom");
        log.info("...", session.getId());
        session.invalidate();
    }

    @RequestMapping("test345")
    public void test345(HttpServletResponse response) throws Exception {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), TestExportVo.class).sheet("模板").doWrite(data());
//        EasyExcelExport.getInstance().getRes();
    }

    @RequestMapping("test123")
    public void test123(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String name = "字段1,字段2,字段3,字段4,字段5,字段6";
        String filed = "col1,col2,col3,col4,col5,col5";
        int[] wids = new int[]{1500,1500,1500,1500,1500,1500};
        Short[] colors = new Short[]{57,57,57,57,57,57};
        TestExportVo vo1 = new TestExportVo();
        TestExportVo vo2 = new TestExportVo();
        TestExportVo vo3 = new TestExportVo();
        TestExportVo vo4 = new TestExportVo();
        List<TestExportVo> vos = new ArrayList<>();
        vo1.setCol1("11");
        vo2.setCol1("12");
        vo3.setCol1("13");
        vo4.setCol1("14");
        vo1.setCol2("21");
        vo2.setCol2("22");
        vo3.setCol2("23");
        vo4.setCol2("24");
        vo1.setCol3("31");
        vo2.setCol3("32");
        vo3.setCol3("33");
        vo4.setCol3("34");
        vo1.setCol4("41");
        vo2.setCol4("42");
        vo3.setCol4("43");
        vo4.setCol4("44");
        vo1.setCol5("51");
        vo2.setCol5("52");
        vo3.setCol5("53");
        vo4.setCol5("54");
        vo1.setCol6("61");
        vo2.setCol6("62");
        vo3.setCol6("63");
        vo4.setCol6("64");
        vos.add(vo1);
        vos.add(vo2);
        vos.add(vo3);
        vos.add(vo4);
        ExportVo exportVo = new ExportVo(name.split(","), filed.split(","), colors, wids, null, "12qwert345", null, vos, true);
        EasyExcelExport.getInstance().exportMergeCell(req, res, exportVo);
    }

    private List<TestExportVo> data() {
        TestExportVo vo1 = new TestExportVo();
        TestExportVo vo2 = new TestExportVo();
        List<TestExportVo> vos = new ArrayList<>();
        vo1.setCol1("123");
        vo2.setCol1("1234");
        vo1.setCol2("abc");
        vo2.setCol2("abcd");
        vo1.setCol3("ABC");
        vo2.setCol3("ABCD");
        vos.add(vo1);
        vos.add(vo2);
        return vos;

    }

}
