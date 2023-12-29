package com.tl.datasource.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

/**
 * description : 请求日志拦截器
 *
 * @author zhoujian
 * date :  2023-12-29 14:16
 */
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        // 请求URL
        String requestUrl = String.valueOf(request.getRequestURL());
        if (!StringUtils.isEmpty(request.getQueryString())) {
            requestUrl = requestUrl + "?" + request.getQueryString();
        }
        // 打印请求头信息
        Enumeration<String> headerNames = request.getHeaderNames();
        String headerValues = "";
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerValues = headerValues + headerName + ":" + request.getHeader(headerName) + ";";
        }
        String paramValues = "";
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            for (String value : values) {
                paramValues = paramValues + key + ":" + value + ";";
            }
        }
        String bodyValues = request.getReader().lines().reduce((a, b) -> a + "\n" + b).orElse("");
        request.setAttribute("reqStartTime", startTime);
        log.info("【请求监控】开始处理 {} header：{} ", requestUrl, headerValues);
        if (!StringUtils.isEmpty(paramValues)) {
            log.info("【请求监控】开始处理 {} param：{}", requestUrl, paramValues);
        }
        if (!StringUtils.isEmpty(bodyValues)) {
            log.info("【请求监控】开始处理 {} body：{}", requestUrl, bodyValues);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 计算请求耗时
        long reqEndTime = System.currentTimeMillis();
        // 请求URL
        String requestUrl = String.valueOf(request.getRequestURL());
        long reqStartTime = 0;
        if (null != request.getAttribute("reqStartTime")) {
            reqStartTime = (long) request.getAttribute("reqStartTime");
        }
        log.info("【请求监控】处理完成 {} 请求状态：{} 耗时：{}ms", requestUrl, response.getStatus(), reqEndTime - reqStartTime);
    }

}
