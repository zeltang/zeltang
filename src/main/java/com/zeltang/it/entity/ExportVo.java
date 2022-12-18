package com.zeltang.it.entity;

import lombok.Data;

import java.util.List;

@Data
public class ExportVo {

    //列头名称
    private String[] names;

    //对应的属性名称，与列头一一对应
    private String[] fileds;

    //列头背景色
    private Short[] backgoundColors;

    // 列宽，与列头一一对应，为null标识不设置列宽
    private int[] columnWidths;

    //数据集合
    private List<?> data;

    //扩展属性，用于处理特殊业务
    private String ext;

    //文件名前缀
    private String fileNamePre;

    // 水印内容
    private String waterText;

    //水印图片模板路径
    private String imagePath;

    // Excel是否加密
    private boolean excelProtect;

    public ExportVo(String[] names, String[] fileds, List<?> data) {
        this.names = names;
        this.fileds = fileds;
        this.data = data;
    }

    public ExportVo(String[] names, String[] fileds, Short[] backgoundColors, List<?> data){
        this.names = names;
        this.fileds=fileds;
        this.backgoundColors = backgoundColors;
        this.data = data;
    }

    public ExportVo(String[] names, String[] fileds, Short[] backgoundColors, String fileNamePre, List<?> data){
        this.names = names;
        this.fileds=fileds;
        this.backgoundColors = backgoundColors;
        this.fileNamePre = fileNamePre;
        this.data = data;
    }

    public ExportVo(String[] names, String[] fileds, Short[] backgoundColors, int[] columnWidths, String fileNamePre, String waterText, String imagePath, List<?> data){
        this.names = names;
        this.fileds=fileds;
        this.backgoundColors = backgoundColors;
        this.columnWidths = columnWidths;
        this.fileNamePre = fileNamePre;
        this.waterText = waterText;
        this.imagePath = imagePath;
        this.data = data;
    }

    public ExportVo(String[] names, String[] fileds, Short[] backgoundColors, int[] columnWidths, String fileNamePre, String waterText, String imagePath, List<?> data, boolean excelProtect){
        this.names = names;
        this.fileds=fileds;
        this.backgoundColors = backgoundColors;
        this.columnWidths = columnWidths;
        this.fileNamePre = fileNamePre;
        this.waterText = waterText;
        this.imagePath = imagePath;
        this.data = data;
        this.excelProtect = excelProtect;
    }
}
