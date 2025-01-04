package com.yyz.generate.controller;

import com.yyz.generate.pojo.Form;
import com.yyz.generate.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 控制层
 *
 * @author: yangyz
 * @date: 2024-05-25 16:55
 */
@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    private TableService tableService;

    /**
     * 生成全部表
     *
     * @param form
     * @return
     */
    @PostMapping("/generateAll")
    public void generateAllTable(Form form) {
        tableService.generateAllTable(form);
    }

    /**
     * 生成指定表
     *
     * @param tableName
     * @return
     */
    @GetMapping("/generate/{tableName}")
    public void generateByTableName(@PathVariable("tableName") String tableName) {
        tableService.generateByTableName(tableName);
    }

    /**
     * 获取指定数据库的全部表
     *
     * @return
     */
    @GetMapping("/{dbName}/table/list")
    public List<String> getTable(@PathVariable("dbName")String dbName) {
        List<String> tables = tableService.getTable(dbName);
        return tables;
    }

    /**
     * 获取表字段
     * @param dbName
     * @param tbName
     */
    @GetMapping("/{dbName}/{tbName}")
    public List<Map<String, Object>> getTableColumns(@PathVariable("dbName") String dbName,
                                                     @PathVariable("tbName") String tbName) {
        List<Map<String, Object>> tableColumns = tableService.getTableColumns(dbName, tbName);
        return tableColumns;
    }
}
