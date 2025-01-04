package com.yyz.generate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TableMapper
 *
 * @author: yangyz
 * @date: 2024-05-25 16:57
 */
@Mapper
public interface TableMapper {
    @Select("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema = #{dbName} ORDER BY table_name")
    List<String> getTables(String dbName);

    @Select("SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = #{dbName} AND TABLE_NAME = #{tableName}")
    List<Map<String, Object>> getTableColumns(String dbName,String tableName);
}
