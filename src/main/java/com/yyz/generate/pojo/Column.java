package com.yyz.generate.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * Column
 *
 * @author: yangyz
 * @date: 2024-05-25 17:01
 */
@Data
@Builder
public class Column {
    private String dbName;

    private String tableName;

    private String columnName;

    private String autoIncrement;

    private String generatedColumn;

    private String columnType;

    private int columnLength;

    private String columnRemark;
}
