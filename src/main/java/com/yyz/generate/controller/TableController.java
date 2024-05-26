package com.yyz.generate.controller;

import com.yyz.generate.pojo.Form;
import com.yyz.generate.service.TableService;
import com.yyz.generate.service.impl.GenerateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

/**
 * Controller
 *
 * @author: yangyz
 * @date: 2024-05-25 16:55
 */
@Controller
public class TableController {

    @Autowired
    private TableService tableService;

    @Autowired
    private GenerateServiceImpl generateServiceImpl;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/getTables")
    public String getTables(Form form) throws SQLException {
        generateServiceImpl.generate(form);
        return "index";
    }

    @GetMapping("/test/{tbName}")
    public void getTableColumns(@PathVariable("tbName") String tbName) {
        tableService.getTableColumns(tbName);
    }
}
