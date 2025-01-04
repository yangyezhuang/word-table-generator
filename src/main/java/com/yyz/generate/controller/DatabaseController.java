package com.yyz.generate.controller;

import com.yyz.generate.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层
 *
 * @author: yangyz
 * @date: 2024-05-25 16:55
 */
@RestController
@RequestMapping("/database")
public class DatabaseController {

    @Autowired
    private TableService tableService;

    /**
     * 获取指定数据库的全部表
     *
     * @return
     */
    @GetMapping("/{dbName}/tables")
    public List<String> getTable(@PathVariable("dbName") String dbName) {
        List<String> tables = tableService.getTable(dbName);
        return tables;
    }

}
