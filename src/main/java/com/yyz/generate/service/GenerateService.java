package com.yyz.generate.service;

import com.yyz.generate.pojo.Form;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author yyz
 * @date 2021/12/6 16:06
 * @description
 */
public interface GenerateService {

    DataSource getDataSource(Form form);

    void generateWord(DataSource dataSource, String DbName );

}
