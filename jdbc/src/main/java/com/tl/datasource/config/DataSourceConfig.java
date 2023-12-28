package com.tl.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.tl.datasource.DynamicDataSource;
import com.tl.datasource.plugin.DynamicDataSourceInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * description : 多数据源配置
 *
 * @author kunlunrepo
 * date :  2023-12-28 09:47
 */
@Configuration
public class DataSourceConfig {

    // 创建DruidDataSource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.datasource1")
    public DataSource dataSource1() {
        return DruidDataSourceBuilder.create().build();
    }

    // 创建DruidDataSource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.datasource2")
    public DataSource dataSource2() {
        return DruidDataSourceBuilder.create().build();
    }

    // 创建事务管理器
    @Bean
    public DataSourceTransactionManager transactionManager1(DynamicDataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    // 创建事务管理器
    @Bean
    public DataSourceTransactionManager transactionManager2(DynamicDataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    // 创建动态数据源切换拦截器
    @Bean
    public Interceptor dynamicDataSourceInterceptor() {
        return new DynamicDataSourceInterceptor();
    }
}
