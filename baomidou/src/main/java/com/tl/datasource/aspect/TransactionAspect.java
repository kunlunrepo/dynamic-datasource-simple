package com.tl.datasource.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;

/**
 * description : 事务切面
 *
 * @author kunlunrepo
 * date :  2023-12-28 15:58
 */
@Aspect
@Component
@Slf4j
public class TransactionAspect {

    /***
     * description : 事务提交耗时
     * @param joinPoint
     * @author kunlunrepo
     * @date 2023-12-29 11:29
     * @return java.lang.Object
     */
    @Around("execution(* com.tl.datasource.service.impl.*.*(..))")
    public Object monitorTransactionCommit(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1.获取基本信息
        String className = joinPoint.getTarget().getClass().getName(); // 类名
        String methodName = joinPoint.getSignature().getName(); // 方法名
        Object[] args = joinPoint.getArgs(); // 参数
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(methodName, methodSignature.getParameterTypes());
        Transactional annotation = method.getAnnotation(Transactional.class); // 注解

        // 2.判断是否有事务
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            // 注册事务提交前和提交后的回调
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                long startTime = 0;

                @Override
                public void beforeCommit(boolean readOnly) {
                    startTime = System.currentTimeMillis();

                    if (annotation!= null) {
                        // 事务的传播行为
                        String propagation = annotation.propagation().toString();
                        // 事务的隔离级别
                        String isolation = annotation.isolation().toString();
                        // 事务的超时时间
                        int timeout = annotation.timeout();
                        // 事务的只读
                        boolean txReadOnly = annotation.readOnly();
                        log.info("【事务提交监控】提交前-{} 方法参数：{} 事务传播行为：{}，事务隔离级别：{}，事务超时时间：{}，事务只读：{}",
                                className+"."+methodName, args, propagation, isolation, timeout, txReadOnly);
                    }
                }

                @Override
                public void afterCommit() {
//                    log.info("【事务提交监控】提交后：方法名：{}, 耗时 {}ms 类名：{}", methodName, System.currentTimeMillis() - startTime, className);
                }

                @Override
                public void afterCompletion(int status) {
                    String txStatus = null;
                    if (status == TransactionSynchronization.STATUS_COMMITTED) {
                        txStatus = "提交";
                    } else if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                        txStatus = "回滚";
                    } else if (status == TransactionSynchronization.STATUS_UNKNOWN) {
                        txStatus = "未知";
                    }
                    log.info("【事务提交监控】完成后-{} 状态：{}，耗时：{}ms", className+"."+methodName, txStatus, System.currentTimeMillis() - startTime);
                }
            });
        }

        // 3.执行业务逻辑
        Object result = joinPoint.proceed(); // 继续执行原始方法
        return result;
    }


}
