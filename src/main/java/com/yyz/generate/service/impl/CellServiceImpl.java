package com.yyz.generate.service.impl;

import com.lowagie.text.Cell;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.yyz.generate.service.CellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;

/**
 * TODO
 *
 * @author yangyz
 * @version V1.0
 * @since 2025/1/3 19:55
 */
@Service
public class CellServiceImpl implements CellService {
    private static final Logger logger = LoggerFactory.getLogger(CellServiceImpl.class);

    /**
     * 添加表头到表格
     *
     * @param table
     * @param content
     * @param width
     * @param align
     */
    @Override
    public void addCell(Table table, String content, int width, int align, Font font) {
//        Font font = new Font(Font.TIMES_ROMAN, 5, Font.BOLD);
        try {
            Cell cell = new Cell(new Paragraph(content, font));
            if (width > 0)
                cell.setWidth(width);
            cell.setHorizontalAlignment(align);
            cell.disableBorderSide(15);
            table.addCell(cell);
        } catch (Exception e) {
            logger.info("添加单元格异常：{}", e);
        }
    }

    @Override
    public void addCell(Table table, String content, int width, int align) {
        Font font = new Font(Font.TIMES_ROMAN, 10.5f, Font.NORMAL);
        try {
            Cell cell = new Cell(new Paragraph(content, font));
            if (width > 0)
                cell.setWidth(width);
            cell.setHorizontalAlignment(align);
            cell.disableBorderSide(15);
            table.addCell(cell);
        } catch (Exception e) {
            logger.info("添加单元格异常：{}", e);
        }
    }

    /**
     * @param table
     * @param content
     * @param width
     * @param align
     */
    @Override
    public void addCell(Table table, String content, int width, int align, int flag) {
        try {
            Font font = new Font(Font.TIMES_ROMAN, 10.5f, Font.NORMAL);
            Cell cell = new Cell(new Paragraph(content, font));
            if (width > 0)
                cell.setWidth(width);
            cell.setHorizontalAlignment(align);
            //0---header,有上下边界,1----有下边界
            if (flag == 0) {
                cell.disableBorderSide(12);
                cell.setBorderColorTop(new Color(0, 0, 0));
                cell.setBorderWidthTop(3f);
                cell.setBorderColorBottom(new Color(0, 0, 0));
                cell.setBorderWidthBottom(3f);
//                cell.Border = Rectangle.RIGHT_BORDER | Rectangle.TOP_BORDER | Rectangle.BOTTOM_BORDER;
//                cell.setBorderWidth(3f);
//                cell.setBackgroundColor(new Color(192, 192, 192));
            } else {
                cell.disableBorderSide(13);
                cell.setBorderColorBottom(new Color(0, 0, 0));
                cell.setBorderWidthBottom(3f);
            }
            table.addCell(cell);
        } catch (Exception e) {
            logger.info("添加单元格异常：{}", e);
        }
    }

    /**
     * 添加单元格
     *
     * @param table
     * @param content
     * @param width
     * @param align
     * @param flag
     * @param font
     */
    @Override
    public void addCell(Table table, String content, int width, int align, int flag, Font font) {
        try {
            Cell cell = new Cell(new Paragraph(content, font));
            if (width > 0)
                cell.setWidth(width);
            cell.setHorizontalAlignment(align);
            //0---header,有上下边界,1----有下边界
            if (flag == 0) {
                cell.disableBorderSide(12);
                cell.setBorderColorTop(new Color(0, 0, 0));
                cell.setBorderWidthTop(3f);
                cell.setBorderColorBottom(new Color(0, 0, 0));
                cell.setBorderWidthBottom(3f);
//                cell.Border = Rectangle.RIGHT_BORDER | Rectangle.TOP_BORDER | Rectangle.BOTTOM_BORDER;
//                cell.setBorderWidth(3f);
//                cell.setBackgroundColor(new Color(192, 192, 192));
            } else {
                cell.disableBorderSide(13);
                cell.setBorderColorBottom(new Color(0, 0, 0));
                cell.setBorderWidthBottom(3f);
            }
            table.addCell(cell);
        } catch (Exception e) {
            logger.info("添加单元格异常：{}", e);
        }
    }
}
