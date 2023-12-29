package com.tl.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-27 10:10
 */
@SpringBootApplication
@MapperScan("com.tl.datasource.mapper")
@EnableAspectJAutoProxy(exposeProxy = true) // 启动AOP
@EnableTransactionManagement // 开启事务
public class BaomidouApp {

    public static void main(String[] args) {
        System.out.println("【多数据源-baomidou-启动中...】");
        SpringApplication.run(BaomidouApp.class, args);
        System.out.println("【多数据源-baomidou-启动成功】");

    }

}
