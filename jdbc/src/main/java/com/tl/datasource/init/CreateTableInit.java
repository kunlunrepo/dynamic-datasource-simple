package com.tl.datasource.init;

import com.tl.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * description : 初始化表
 *
 * @author kunlunrepo
 * date :  2023-12-27 17:52
 */
@Component
public class CreateTableInit implements ApplicationListener<ApplicationReadyEvent> {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    // 写
    @Autowired
    DataSource dataSource;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        String databaseName1 = "datasource1";
        DynamicDataSource.name.set("W"); // 切换数据源1
        initTable(databaseName1, dataSource);

        String databaseName2 = "datasource2";
        DynamicDataSource.name.set("R"); // 切换数据源2
        initTable(databaseName2, dataSource);

    }

    private void initTable(String databaseName, DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String friendTableName = "friend";

        // 查询数据库所有表
        String allTables = "SELECT table_name FROM information_schema.tables WHERE table_schema = ? ";
        // 创建friend表
        String createTable = "CREATE TABLE IF NOT EXISTS " + friendTableName + " (id BIGINT(20) NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, PRIMARY KEY (id))";

        try {
            // 获取数据库元数据
            DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            String url = metaData.getURL();
            System.out.println("【应用-初始化】数据库连接：" + url);
            // 所有表
            List<String> tableNames = jdbcTemplate.queryForList(allTables, String.class, databaseName);
            // 遍历表
            if (tableNames.contains(friendTableName)) {
                System.out.println("【应用-初始化】表已存在：" + friendTableName);
            } else {
                // 建表
                jdbcTemplate.execute(createTable);
                System.out.println("【应用-初始化】创建表：" + friendTableName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("应用初始化表结构报错");
        }
    }
}
