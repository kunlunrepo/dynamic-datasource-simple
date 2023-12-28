package com.tl.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * description : 实现基于路由规则的数据源切换
 *
 * @author kunlunrepo
 * date :  2023-12-28 09:53
 */
@Component
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource {

    // 保存当前数据源标识
    public static ThreadLocal<String> name = new ThreadLocal<>();

    // 写
    @Autowired
    DataSource dataSource1;

    // 读
    @Autowired
    DataSource dataSource2;

    // 返回当前数据源标识
    @Override
    protected Object determineCurrentLookupKey() {
        return name.get();
    }

    // 对象创建后设置其属性
    @Override
    public void afterPropertiesSet() {
        // 初始化所有数据源
        Map<Object, Object> targetes = new HashMap<>();
        targetes.put("W", dataSource1);
        targetes.put("R", dataSource2);
        // 设置数据源
        super.setTargetDataSources(targetes);
        // 设置默认数据源
        super.setDefaultTargetDataSource(dataSource1);
        // 加载数据源
        super.afterPropertiesSet();
    }
}
