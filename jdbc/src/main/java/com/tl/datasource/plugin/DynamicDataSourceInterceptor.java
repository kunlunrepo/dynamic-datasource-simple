package com.tl.datasource.plugin;

import com.tl.datasource.DynamicDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-28 12:26
 */
@Intercepts(
        {@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
                        ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 拿到当前方法(update、query)所有参数
        Object[] objects = invocation.getArgs();

        // MappedStatement 封装CRUD所有的元素和SQL
        MappedStatement ms = (MappedStatement) objects[0];
        // 读方法
        if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {

            DynamicDataSource.name.set("R");
        } else {
            // 写方法
            DynamicDataSource.name.set("W");
        }
        System.out.println(invocation.getMethod().getName() +"Intercepts自动切换数据源");
        // 修改当前线程要选择的数据源的key
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
