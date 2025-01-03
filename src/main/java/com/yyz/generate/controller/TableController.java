package com.yyz.generate.controller;

import com.yyz.generate.pojo.Form;
import com.yyz.generate.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

/**
 * 控制层
 *
 * @author: yangyz
 * @date: 2024-05-25 16:55
 */
@Controller
public class TableController {

    @Autowired
    private TableService tableService;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 生成全部表
     *
     * @param form
     * @return
     * @throws SQLException
     */
    @PostMapping("/generate/all")
    public String generateAllTable(Form form) throws SQLException {
        tableService.generateAllTable(form);
        return "index";
    }

    /**
     * 生成指定表
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    @PostMapping("/generate/{tableName}")
    public String generateByTableName(@PathVariable("tableName") String tableName) throws SQLException {
        tableService.generateByTableName(tableName);
        return "index";
    }

    /**
     * @param tbName
     */
    @GetMapping("/test/{tbName}")
    public void getTableColumns(@PathVariable("tbName") String tbName) {
        tableService.getTableColumns(tbName);
    }
}
