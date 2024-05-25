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
    List<Map<String, Object>> getTables();

    @Select("SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'test_db' AND TABLE_NAME = #{tbName}")
    List<Map<String, Object>> getTableColumns(String tableName);
}
