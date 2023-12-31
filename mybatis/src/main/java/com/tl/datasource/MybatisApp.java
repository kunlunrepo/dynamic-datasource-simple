package com.tl.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-27 10:10
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true) // 启动AOP
public class MybatisApp {

    public static void main(String[] args) {
        System.out.println("【多数据源-mybatis-启动中...】");
        SpringApplication.run(MybatisApp.class, args);
        System.out.println("【多数据源-mybatis-启动成功】");

    }

}
