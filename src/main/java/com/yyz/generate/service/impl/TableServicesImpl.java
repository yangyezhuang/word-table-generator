package com.yyz.generate.service.impl;

import com.yyz.generate.mapper.TableMapper;
import com.yyz.generate.pojo.Column;
import com.yyz.generate.service.TableService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TableServicesImpl
 *
 * @author: yangyz
 * @date: 2024-05-25 16:57
 */
@Service
public class TableServicesImpl implements TableService {

    private static final Logger logger = LoggerFactory.getLogger(TableServicesImpl.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private TableMapper tableMapper;


    /**
     * 获取表的元数据 表结构
     * @return
     */
    public List<Column> getUserMeta() {
        List<Column> columnList = new ArrayList<>();
        try {
            // 获取连接、获取元数据
            DatabaseMetaData metaData = sqlSessionFactory.openSession()
                    .getConnection()
                    .getMetaData();
            // 获取 表字段
            ResultSet columns = metaData.getColumns("test", null, "user", null);
            // 对结果集 ResultSet 操作
            while (columns.next()) {
                Column columnBean = Column.builder()
                        .dbName(columns.getString("TABLE_CAT"))
                        .tableName(columns.getString("TABLE_NAME"))
                        .columnName(columns.getString("COLUMN_NAME"))
                        .autoIncrement(columns.getString("IS_AUTOINCREMENT"))
                        .generatedColumn(columns.getString("IS_GENERATEDCOLUMN"))
                        .columnType(columns.getString("TYPE_NAME"))
                        .columnLength(columns.getInt("COLUMN_SIZE"))
                        .columnRemark(columns.getString("REMARKS"))
                        .build();
                columnList.add(columnBean);
            }
            logger.info("columnList" + columnList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnList;
    }

    @Override
    public void getTableColumns(String tableName) {
        List<Map<String, Object>> tableColumns = tableMapper.getTableColumns(tableName);
        logger.info(tableColumns.toString());
    }
}
