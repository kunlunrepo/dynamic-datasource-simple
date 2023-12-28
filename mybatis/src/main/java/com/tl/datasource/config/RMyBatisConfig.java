package com.tl.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-28 11:25
 */
@Configuration
@MapperScan(basePackages = "com.tl.datasource.mapper.r",
sqlSessionFactoryRef = "rSqlSessionFactory")
public class RMyBatisConfig {

    // 创建数据源
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.datasource2")
    public DataSource dataSource2() {
        return DruidDataSourceBuilder.create().build();
    }

    // 创建SqlSessionFactory
    @Bean
    @Primary
    public SqlSessionFactory rSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory =  new SqlSessionFactoryBean();
        // 指定主库
        sessionFactory.setDataSource(dataSource2());
        return sessionFactory.getObject();
    }

    // 创建事务管理器
    @Bean
    public DataSourceTransactionManager rTransactionManager() {
        return new DataSourceTransactionManager(dataSource2());
    }

    // 创建事务模板
    @Bean
    public TransactionTemplate rTransactionTemplate() {
        return new TransactionTemplate(rTransactionManager());
    }
}
