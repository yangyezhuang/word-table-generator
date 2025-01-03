package com.yyz.generate.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;

/**
 * 添加单元格-添加表头到表格
 *
 * @author yangyz
 * @version V1.0
 * @since 2025/1/3 19:51
 */
public interface CellService {
    /**
     * 添加表头到表格
     *
     * @param table
     * @param content
     * @param width
     * @param align
     */
    void addCell(Table table, String content, int width, int align, Font font);

    void addCell(Table table, String content, int width, int align);

    void addCell(Table table, String content, int width, int align, int flag);

    void addCell(Table table, String content, int width, int align, int flag, Font font);
}
