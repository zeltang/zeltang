package com.zeltang.it.handler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @ClassName WaterMarkCellHandler
 * @Author tangzelong
 * @Date 2020/10/22 13:56
 * @Version 1.0
 */
@RequiredArgsConstructor
public class WaterMarkCellHandler implements CellWriteHandler {

    private final Short[] COLORS;

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head,
                                 Integer relativeRowIndex, Boolean isHead) {
        // 重写表头header样式
        if (isHead && cell.getRowIndex() == 0) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();
            //居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            //设置边框
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            //设置单元格背景色
            cellStyle.setFillForegroundColor(COLORS[cell.getColumnIndex()]);
            Font font = workbook.createFont();
            font.setColor(IndexedColors.BLACK.getIndex());
            //设置标题字体背景色
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
        }
    }
}
