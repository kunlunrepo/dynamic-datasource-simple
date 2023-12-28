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
@MapperScan(basePackages = "com.tl.datasource.mapper.w", sqlSessionFactoryRef = "wSqlSessionFactory")
public class WMyBatisConfig {

    // 创建数据源
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.datasource1")
    public DataSource dataSource1() {
        return DruidDataSourceBuilder.create().build();
    }

    // 创建SqlSessionFactory
    @Bean
    @Primary
    public SqlSessionFactory wSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory =  new SqlSessionFactoryBean();
        // 指定主库
        sessionFactory.setDataSource(dataSource1());
        return sessionFactory.getObject();
    }

    // 创建事务管理器
    @Bean
    public DataSourceTransactionManager wTransactionManager() {
        return new DataSourceTransactionManager(dataSource1());
    }

    // 创建事务模板
    @Bean
    public TransactionTemplate wTransactionTemplate() {
        return new TransactionTemplate(wTransactionManager());
    }
}
