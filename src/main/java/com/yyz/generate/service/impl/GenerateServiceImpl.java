package com.yyz.generate.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.yyz.generate.pojo.Form;
import com.yyz.generate.pojo.TableFiled;
import com.yyz.generate.pojo.TableInfo;
import com.yyz.generate.service.CellService;
import com.yyz.generate.service.GenerateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangyz
 * @date 2021/12/6 16:06
 * @description
 */
@Service
public class GenerateServiceImpl implements GenerateService {
    private static final Logger logger = LoggerFactory.getLogger(GenerateServiceImpl.class);

    @Autowired
    private CellService cellService;

    private static final String FONT_STYLE = "C:\\Windows\\Fonts\\simsunb.ttf";

    @Value("${system.gen_file_path}")
    public String GEN_FILE_PATH;

    @Value("${system.file_suffix}")
    public String FILE_SUFFIX = "三线表.doc";

    @Value("${system.host}")
    private String host;

    @Value("${system.port}")
    private int port;

    @Value("${system.username}")
    private String username;

    @Value("${system.password}")
    private String password;

    @Value("${system.database}")
    private String database;


    public void generate(Form form) {
        try {
            DataSource ds = getDataSource(form);
            generateWord(ds, form.getDbName());
        } catch (Exception e) {
            logger.info("表格生成异常{}：", e);
        }
    }


    /**
     * 连接数据库
     *
     * @return
     */
    @Override
    public DataSource getDataSource(Form form) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl(String.format("jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=UTF-8&useSSL=false", form.getDbUrl(), form.getDbName()));
        datasource.setUsername(form.getUsername());
        datasource.setPassword(form.getPassword());
        datasource.setInitialSize(1);
        datasource.setMinIdle(1);
        datasource.setMaxActive(3);
        datasource.setMaxWait(60000);
        return datasource;
    }


    /**
     * 生成word文档
     *
     * @param dataSource
     * @return: void
     */
    public void generateWord(DataSource dataSource, String dbName) {
        List<TableInfo> tables = getTableInfos(dataSource, dbName);
        Document document = new Document(PageSize.A4);
        try {
            File dir = new File(GEN_FILE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = GEN_FILE_PATH + File.separator + dbName + FILE_SUFFIX;
            File file = new File(fileName);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            file.createNewFile();

            // 写入文件信息
            RtfWriter2.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // gebTableInfoDesc(document, tables);
            genTableStructDesc(document, tables, dataSource);
            document.close();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        logger.info("所有表【共{}个】处理完成", tables.size());
    }


    /**
     * 生成表清单
     *
     * @param document
     * @param tables
     */
    private void gebTableInfoDesc(Document document, List<TableInfo> tables) {
        try {
            Paragraph p = new Paragraph("表清单描述", new Font(Font.TIMES_ROMAN, 24, Font.NORMAL, new Color(0, 0, 0)));
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);

            logger.info("表清单开始生成");
            Table table = new Table(2);
            int[] widths = new int[]{500, 900};
            table.setWidths(widths);
            table.setBorderWidth(1);
            table.setPadding(0);
            table.setSpacing(0);

            //添加表头
            Cell headerCell = new Cell("表名");
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setBackgroundColor(new Color(192, 192, 192));
            table.addCell(headerCell);

            headerCell = new Cell("表描述");
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setBackgroundColor(new Color(192, 192, 192));
            table.addCell(headerCell);
            table.endHeaders();
            // 添加内容行
            for (TableInfo tableInfo : tables) {
                addCell(table, tableInfo.getTblName());
                addCell(table, tableInfo.getTblComment());
            }
            document.add(table);
            logger.info("表清单生成结束");
        } catch (Exception e) {
            logger.info("表清单生成异常：{}", e);
        }
    }


    /**
     * 生成表结构清单
     *
     * @param document
     * @param tables
     * @param ds
     * @throws DocumentException
     * @throws SQLException
     * @throws IOException
     */
    private void genTableStructDesc(Document document, List<TableInfo> tables, DataSource ds) throws DocumentException, SQLException, IOException {
        Paragraph p = new Paragraph("表结构描述", new Font(Font.TIMES_ROMAN, 18, Font.NORMAL, new Color(0, 0, 0)));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        logger.info("共{}个表待处理", tables.size());
        int colNum = 9;
        //循环处理每一张表
        for (int i = 0; i < tables.size(); i++) {
            TableInfo tableInfo = tables.get(i);
            String tblName = tableInfo.getTblName();
            String tblComment = tableInfo.getTblComment();

            logger.info("> {} 表开始处理", tableInfo);
            // 写入表说明
//            String tblTile = "" + (i + 1) + " 表名称:" + tblName + "（" + tblComment + "）";
//            Paragraph paragraph = new Paragraph(tblTile);
//            document.add(paragraph);

            List<TableFiled> fileds = getTableFields(ds, tables.get(i).getTblName());
            Table table = new Table(colNum);
            int[] widths = new int[]{150, 200, 150, 100, 80, 80, 160, 100, 120};
            table.setWidths(widths);
//            table.setBorderWidth(1);
            table.setPadding(0);
            table.setSpacing(0);

            // 添加表名行
            String tblInfo = StringUtils.isBlank(tblComment) ? tblName : String.format("%s(%s)", tblName, tblComment);
//            Cell headerCell = new Cell(tblInfo);
//            headerCell.disableBorderSide(15);
//            headerCell.setColspan(colNum);
//            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(headerCell);
            Paragraph ph = new Paragraph(tblInfo, new Font(Font.TIMES_ROMAN, 14, Font.NORMAL, new Color(0, 0, 0)));
            ph.setAlignment(Element.ALIGN_CENTER);
            document.add(ph);

            BaseFont bfComic0 = BaseFont.createFont(FONT_STYLE, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfComic0, 10.5f);

            //添加表头行
            addCell(table, "字段名", 0, font);
            addCell(table, "字段描述", 0, font);
            addCell(table, "数据类型", 0, font);
            addCell(table, "长度", 0, font);
            addCell(table, "为空", 0, font);
            addCell(table, "是否主键", 0, font);
            addCell(table, "约束", 0, font);
            addCell(table, "缺省值", 0, font);
            addCell(table, "备注", 0, font);
            table.endHeaders();

            int k;
            // 表格的主体
            for (k = 0; k < fileds.size() - 1; k++) {
                TableFiled field = fileds.get(k);
                addCell(table, field.getField());
                addCell(table, field.getComment(), font);
                addCell(table, field.getType());
                addCell(table, field.getLength());
                addCell(table, field.isNull() ? "是" : "否", font);
                addCell(table, field.getKey().equals("PRI") ? "是" : "否", font);
                addCell(table, "", font); // 约束
                addCell(table, field.getDefaultVal()); //缺省值
                addCell(table, field.getExtra());
            }

            /**
             * 生成表格，最后一行
             */
            if (k == fileds.size() - 1) {
                TableFiled field = fileds.get(k);
                addCell(table, field.getField(), 1);
                addCell(table, field.getComment(), 1, font);
                addCell(table, field.getType(), 1);
                addCell(table, field.getLength(), 1);
                addCell(table, field.isNull() ? "是" : "否", 1, font);
                addCell(table, field.getKey().equals("PRI") ? "是" : "否", 1, font);
                addCell(table, "", 1, font); //约束
                addCell(table, field.getDefaultVal(), 1); //缺省值
                addCell(table, field.getExtra(), 1);
            }
            table.setBorder(2);
            table.setBorderWidth(15f);
            document.add(table);
            logger.info("处理{}表结束", tableInfo);
        }
    }

    private void addCell(Table table, String content) {
        cellService.addCell(table, content, -1, Element.ALIGN_CENTER);
    }

    private void addCell(Table table, String content, int flag) {
        cellService.addCell(table, content, -1, Element.ALIGN_CENTER, flag);
    }

    private void addCell(Table table, String content, Font font) {
        cellService.addCell(table, content, -1, Element.ALIGN_CENTER, font);
    }

    private void addCell(Table table, String content, int flag, Font font) {
        cellService.addCell(table, content, -1, Element.ALIGN_CENTER, flag, font);
    }


    /**
     * 获取表信息
     *
     * @param dataSource
     * @param databaseName
     * @return
     */
    private List<TableInfo> getTableInfos(DataSource dataSource, String databaseName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<TableInfo> list = new ArrayList();
        try {
            conn = dataSource.getConnection();
            String sql = "select TABLE_NAME,TABLE_TYPE,TABLE_COMMENT from information_schema.tables where table_schema =? order by table_name";

            stmt = conn.prepareStatement(sql);
            setParameters(stmt, Arrays.asList(databaseName));

            rs = stmt.executeQuery();
            ResultSetMetaData rsMeta = rs.getMetaData();

            while (rs.next()) {
                TableInfo row = new TableInfo();
                row.setTblName(rs.getString(1));
                row.setTblType(rs.getString(2));
                row.setTblComment(rs.getString(3));
                list.add(row);
            }
        } catch (SQLException e) {
            logger.info("获取表信息异常：{}", e);
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(stmt);
            JdbcUtils.close(conn);
        }
        return list;
    }


    /**
     * 获取表字段
     *
     * @param ds
     * @param tblName
     * @return
     */
    private List<TableFiled> getTableFields(DataSource ds, String tblName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TableFiled> list = new ArrayList();
        try {
            conn = ds.getConnection();
            //返回的列顺序是: Field,Type,Collation,Null,Key,Default,Extra,Privileges,Comment
            String sql = "SHOW FULL FIELDS FROM " + tblName;
            //返回的列顺序是: Field,Type,Null,Key,Default,Extra
//            sql = "show columns FROM " + tblName;
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            ResultSetMetaData rsMeta = rs.getMetaData();

            while (rs.next()) {
                TableFiled field = new TableFiled();
                field.setField(rs.getString(1));
                String type = rs.getString(2);
                String length = "";
                if (type.contains("(")) {
                    int idx = type.indexOf("(");
                    length = type.substring(idx + 1, type.length() - 1);
                    type = type.substring(0, idx);
                }
                field.setType(type);
                field.setLength(length);
                field.setNull(rs.getString(4).equalsIgnoreCase("YES") ? true : false);
                field.setKey(rs.getString(5));
                field.setDefaultVal(rs.getString(6));
                field.setExtra(rs.getString(7));
                field.setComment(rs.getString(9));
                list.add(field);
            }
        } catch (Exception e) {
            logger.info("获取表字段异常：{}", e);
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(stmt);
            JdbcUtils.close(conn);
        }
        return list;
    }


    /**
     * 拼接参数
     *
     * @param stmt
     * @param parameters
     * @throws SQLException
     */
    private void setParameters(PreparedStatement stmt, List<Object> parameters) {
        try {
            for (int i = 0, size = parameters.size(); i < size; ++i) {
                Object param = parameters.get(i);
                stmt.setObject(i + 1, param);
            }
        } catch (Exception e) {
            logger.info("拼接参数异常：{}", e);
        }
    }

}
