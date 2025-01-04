package com.yyz.generate.service;

import com.yyz.generate.pojo.Form;

import java.util.List;
import java.util.Map;

/**
 * TableService
 *
 * @author: yangyz
 * @date: 2024-05-25 16:57
 */
public interface TableService {

    void generateAllTable(Form form);

    void generateByTableName(String tableName);

    List<String> getTable(String dbName);

    List<Map<String, Object>> getTableColumns(String dbName, String tableName);

    //    List<Map<String, Object>> getTableColumns(String tableName);
}
