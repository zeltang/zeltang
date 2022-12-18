package com.zeltang.it.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


import com.zeltang.it.utils.ExcelWaterRemarkUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;

/**
 * 
 * @author lyt
 * 2017年7月19日
 */
public class poiexe {
	
	public static void main(String[] args) throws IOException {
		//读取excel文件
		Workbook wb = new XSSFWorkbook(new FileInputStream("E:\\dan.xlsx"));
//		List<Integer> list = Arrays.asList(4000,10000,3000,3000,2000,3000,10000,4000,10000,3000,3000,6000,3000,6000,6000,6000, 4000,4000);
		List<Integer> list = new ArrayList<>();
//		Workbook wb = new XSSFWorkbook(new FileInputStream("E:\\duo.xlsx"));
		//设置水印图片路径
//		String imgPath = "D:\\模板.png";

		BufferedImage image = ImageIO.read(new File("E:\\1.png"));
		// 创建输出流
		ByteArrayOutputStream byteArrayOutputStream = new  ByteArrayOutputStream();
		// 写入流
		ImageIO.write(image, "png", byteArrayOutputStream);
		// 清流
		byteArrayOutputStream.flush();
		// 转为byte[]
		byte[] byteArray = byteArrayOutputStream.toByteArray();

//		ImageUtils.createWaterMark("文字水印效果", byteArray);
		//获取excel sheet个数
		int sheets = wb.getNumberOfSheets();
		//循环sheet给每个sheet添加水印
		for (int i = 0; i < sheets; i++) {
			Sheet sheet = wb.getSheetAt(i);
//			CellStyle blackStyle = wb.createCellStyle();
//			blackStyle.setWrapText(true);
			sheet.createFreezePane(2,0,0,0);
			int rowNum = sheet.getFirstRowNum() + sheet.getLastRowNum();
			for (int j = 0; j <= rowNum; j++) {
				if (j == 0) {
					continue;
				}
				Row row = sheet.getRow(j);
				for (int k =0; k < list.size(); k++) {
					Cell cell = row.getCell(k);
					sheet.setColumnWidth(k, list.get(k));
					String stringCellValue = row.getCell(k).getStringCellValue();
					CellStyle style = wb.createCellStyle(); //Create new style
					style.setWrapText(true); //Set wordwrap
					cell.setCellStyle(style); //Apply style to cell
					cell.setCellValue(stringCellValue);
				}
			}


//			sheet.setColumnWidth(1, 2000); //Set column width, you'll probably want to tweak the second int
//			CellStyle style = wb.createCellStyle(); //Create new style
//			style.setWrapText(true); //Set wordwrap
//			cell.setCellStyle(style); //Apply style to cell
//			cell.setCellValue(header); //Write header
//			for (i=0; i < 18; i++) {
//				sheet.setColumnWidth(1, 2000);
//			}
			//excel加密只读
//			sheet.protectSheet("123456");
			//获取excel实际所占行
			int row = sheet.getFirstRowNum() + sheet.getLastRowNum();
			//获取excel实际所占列
			int cell = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum() + 1;
			//根据行与列计算实际所需多少水印
			ExcelWaterRemarkUtils.putWaterRemarkToExcel(wb, sheet, byteArray, 0, 0, 15, 15, cell / 5 + 1, row / 5 + 1, 0, 0);
//			ExcelWaterRemarkUtils.putWaterRemarkToExcel(wb, sheet, byteArray, 0, 0, 12, 10, cell / 10 + 1, row / 10 + 1, 0, 0);

		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		wb.close();
		byte[] content = os.toByteArray();
		// Excel文件生成后存储的位置。
		File file1 = new File("E:\\1585"+"-"+System.currentTimeMillis()+".xlsx");
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(file1);
			fos.write(content);
			os.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
