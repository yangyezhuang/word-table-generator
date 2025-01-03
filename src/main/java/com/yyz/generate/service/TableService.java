package com.yyz.generate.service;

import com.yyz.generate.pojo.Form;

/**
 * TableService
 *
 * @author: yangyz
 * @date: 2024-05-25 16:57
 */
public interface TableService {
    //    List<Map<String, Object>> getTableColumns(String tableName);
    void getTableColumns(String tableName);

    void generateAllTable(Form form);

    void generateByTableName(String tableName);
}
