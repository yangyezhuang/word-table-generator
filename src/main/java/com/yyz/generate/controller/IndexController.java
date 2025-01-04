package com.yyz.generate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 试视图控制层
 *
 * @author: yangyz
 * @date: 2024-05-25 16:55
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
