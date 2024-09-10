package com.zeltang.it.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.zeltang.it.entity.ExportVo;
import com.zeltang.it.entity.TestExportVo;
import com.zeltang.it.export.CellMergeHandler;
import com.zeltang.it.export.WaterMarkCellHandler;
import com.zeltang.it.export.WaterMarkSheetHandler;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName
 * @Author tangzelong
 * @Date 2020/10/10 11:10
 * @Version 1.0
 */
public class EasyExcelExport {

    private  static EasyExcelExport easyExcelExport;

    private EasyExcelExport(){}

    public  static EasyExcelExport getInstance(){
        if(notEmpty(easyExcelExport)){
            return easyExcelExport;
        }
        easyExcelExport = new EasyExcelExport();
        return easyExcelExport;
    }

    public void export(HttpServletRequest request, HttpServletResponse response, ExportVo exportVo) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            EasyExcel.write(response.getOutputStream()).inMemory(true)
                    // 自定义表格的标题和内容的样式
                    .registerWriteHandler(setHorizontalCellStyleStrategy())
                    // 自定义单元格样式（颜色，字体等等）
                    .registerWriteHandler(new WaterMarkCellHandler(exportVo.getBackgoundColors()))
                    // 设置单元格背景，换行，列宽，工作表保护...
                    .registerWriteHandler(new WaterMarkSheetHandler(exportVo))
                    // 这里放入动态头
                    .head(head(exportVo.getNames())).sheet("sheet1")
                    // 当然这里数据也可以用 List<List<String>> 去传入
                    .doWrite(data(exportVo.getFileds(), exportVo.getData()));

        } catch (Exception e) {
        }
    }

    public void exportMergeCell(HttpServletRequest request, HttpServletResponse response, ExportVo exportVo) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            EasyExcel.write(response.getOutputStream()).inMemory(true)
                    // 自定义表格的标题和内容的样式
                    .registerWriteHandler(setHorizontalCellStyleStrategy())
                    // 自定义单元格样式（颜色，字体等等）
                    .registerWriteHandler(new WaterMarkCellHandler(exportVo.getBackgoundColors()))
                    // 设置单元格背景，换行，列宽，工作表保护...
                    .registerWriteHandler(new WaterMarkSheetHandler(exportVo))
                    // 合并单元格，每隔2行进行合并，从第0列开始
                    .registerWriteHandler(new LoopMergeStrategy(2, 0))
                    .registerWriteHandler(new LoopMergeStrategy(2, 1))
                    // 这里放入动态头
                    .head(head(exportVo.getNames())).sheet("sheet1")
                    // 当然这里数据也可以用 List<List<String>> 去传入
                    .doWrite(data(exportVo.getFileds(), exportVo.getData()));

        } catch (Exception e) {
        }
    }

    public void exportMerge(HttpServletRequest request, HttpServletResponse response, ExportVo exportVo) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            EasyExcel.write(response.getOutputStream()).inMemory(true)
                    // 自定义表格的标题和内容的样式
                    .registerWriteHandler(setHorizontalCellStyleStrategy())
                    // 自定义单元格样式（颜色，字体等等）
                    .registerWriteHandler(new CellMergeHandler(1, new int[]{1, 1}))
                    // 设置单元格背景，换行，列宽，工作表保护...
                    .registerWriteHandler(new WaterMarkSheetHandler(exportVo))
                    // 这里放入动态头
                    .head(head(exportVo.getNames())).sheet("sheet1")
                    // 当然这里数据也可以用 List<List<String>> 去传入
                    .doWrite(data(exportVo.getFileds(), exportVo.getData()));
        } catch (Exception e) {
        }
    }

    public void exportMultiSheet(HttpServletRequest request, HttpServletResponse response, ExportVo exportVo) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            for (int i = 0; i<=1; i++) {
                EasyExcel.write(response.getOutputStream()).inMemory(true)
                        // 自定义表格的标题和内容的样式
                        .registerWriteHandler(setHorizontalCellStyleStrategy())
                        // 自定义单元格样式（颜色，字体等等）
                        .registerWriteHandler(new WaterMarkCellHandler(exportVo.getBackgoundColors()))
                        // 设置单元格背景，换行，列宽，工作表保护...
                        .registerWriteHandler(new WaterMarkSheetHandler(exportVo))
                        // 这里放入动态头
                        .head(head(exportVo.getNames())).sheet("sheet1"+i)
                        // 当然这里数据也可以用 List<List<String>> 去传入
                        .doWrite(data(exportVo.getFileds(), exportVo.getData()));
            }

        } catch (Exception e) {
        }
    }

    /**
     * 自定义表格的标题和内容的样式
     * 如果此处单元格样式都不符合，需要自定义拦截器去实现
     *
     * @return
     */
    public static HorizontalCellStyleStrategy setHorizontalCellStyleStrategy(){
        // 1.头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)10);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 2.内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置背景色---这里需要指定FillPatternType为FillPatternType.SOLID_FOREGROUND，不然无法显示背景颜色.如果头默认了FillPatternType所以可以不指定
        // contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 设置内容文本边框样式
        // contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        // contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        // contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        // contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }

    /**
     * 组建动态数据
     * @param fileds
     * @param data
     * @return
     */
    private List<List<Object>> data(String[] fileds, List<?> data) {
        List<List<Object>> list = new ArrayList<List<Object>>();
        Object[] nullArgs = new Object[0];
        for (Object object : data) {
            List<Object> res = new ArrayList<>();
            Class<?> clazz = object.getClass();
            for (String fieldName : fileds) {
                Object fieldValue = null;
                Method method = null;
                try {
                    method = clazz.getMethod("get_" + fieldName);
                    fieldValue = method.invoke(object, nullArgs);
                } catch (Exception e) {
                    try {
                        method = clazz.getMethod("get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1));
                        fieldValue = method.invoke(object, nullArgs);
                    } catch (Exception e2) {
                        // do nothing;
                    }
                }
                if (isEmpty(fieldValue) ) {
                    fieldValue = "";
                }
                res.add(fieldValue);
            }
            list.add(res);
        }
        return list;
    }

    /**
     * 组建动态头
     * @param names
     * @return
     */
    private List<List<String>> head(String[] names) {
        List<List<String>> list = new ArrayList<List<String>>();
        for (int i = 0; i < names.length; i++) {
            List<String> head = new ArrayList<String>();
            head.add(names[i]);
            list.add(head);
        }
        return list;
    }

//    private List<List<String>> head() {
//        List<List<String>> list = new ArrayList<List<String>>();
//        List<String> head0 = new ArrayList<String>();
//        head0.add("字符串" + System.currentTimeMillis());
//        List<String> head1 = new ArrayList<String>();
//        head1.add("数字" + System.currentTimeMillis());
//        List<String> head2 = new ArrayList<String>();
//        head2.add("日期" + System.currentTimeMillis());
//        list.add(head0);
//        list.add(head1);
//        list.add(head2);
//        return list;
//    }
//
//    private List<List<Object>> dataList() {
//        List<List<Object>> list = new ArrayList<List<Object>>();
//        for (int i = 0; i < 10; i++) {
//            List<Object> data = new ArrayList<Object>();
//            data.add("字符串" + i);
//            data.add(new Date());
//            data.add(0.56);
//            list.add(data);
//        }
//        return list;
//    }
//
//
//    private List<DemoData> data() {
//        List<DemoData> list = new ArrayList<DemoData>();
//        for (int i = 0; i < 10; i++) {
//            DemoData data = new DemoData();
//            data.setString("字符串" + i);
//            data.setDate(new Date());
//            data.setDoubleData(0.56);
//            list.add(data);
//        }
//        return list;
//    }
//
//
//    @Data
//    public class DemoData {
//        private String string;
//        private Date date;
//        private Double doubleData;
//    }

    public static boolean notEmpty(Object arg) {
        return !isEmpty(arg);
    }


    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<String>();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<String>();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(0.56);
            list.add(data);
        }
        return list;
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
