package com.tl.datasource.config;

import com.tl.datasource.interceptor.RequestLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description : 拦截器配置
 *
 * @author zhoujian
 * date :  2023-12-29 14:18
 */
@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLogInterceptor());
    }

}
