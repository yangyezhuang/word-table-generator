package com.yyz.generate.pojo;

import lombok.Data;

/**
 * TableFiled
 *
 * @author: YZ.YANG
 * @date: 2023-12-24 16:11
 */
@Data
public class TableFiled {
    private String field;
    private String type;
    private String length;
    private boolean isNull;
    private String key;
    private String defaultVal;
    private String extra;
    private String comment;
}
