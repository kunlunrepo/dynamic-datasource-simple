package com.tl.datasource.aspect;

import com.tl.datasource.DynamicDataSource;
import com.tl.datasource.anno.WR;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * description : 注解切面
 *
 * @author kunlunrepo
 * date :  2023-12-28 10:16
 */
@Component
@Aspect
public class DynamicDataSourceAspect implements Ordered {


    // 前置
    @Before ("within(com.tl.datasource.service.impl.*) && @annotation(wr)")
    public void before(JoinPoint joinPoint, WR wr) {
        // 设置数据源名称
        String name = wr.value();
        DynamicDataSource.name.set(name);

        String name1 = joinPoint.getSignature().getName();

        System.out.println(name1 + "的数据源" + name);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
