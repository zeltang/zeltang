package com.zeltang.it.controller;

import com.alibaba.excel.EasyExcel;
import com.zeltang.it.entity.ExportVo;
import com.zeltang.it.entity.TestExportVo;
import com.zeltang.it.test1.service.ITestService;
import com.zeltang.it.test1.vo.ProductBatchImportVo;
import com.zeltang.it.utils.EasyExcelExport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        String name = "字段1,字段2,字段3";
        String filed = "col1,col2,col3";
        int[] wids = new int[]{1500,1500,1500};
        Short[] colors = new Short[]{57,57,57};
        TestExportVo vo1 = new TestExportVo();
        TestExportVo vo2 = new TestExportVo();
        List<TestExportVo> vos = new ArrayList<>();
        vo1.setCol1("123");
        vo2.setCol1("123");
        vo1.setCol2("abc");
        vo2.setCol2("abc");
        vo1.setCol3("ABC");
        vo2.setCol3("ABC");
        vos.add(vo1);
        vos.add(vo2);
        ExportVo exportVo = new ExportVo(name.split(","), filed.split(","), colors, wids, null, "12qwert345", null, vos, true);
        EasyExcelExport.getInstance().exportMultiSheet(req, res, exportVo);
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
