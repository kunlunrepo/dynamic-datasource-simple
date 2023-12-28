package com.tl.datasource.aop;

import com.tl.datasource.anno.MultiTransactional;
import com.tl.datasource.service.transaction.ComboTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MultiTransactionAop {

    private final ComboTransaction comboTransaction;

    @Autowired
    public MultiTransactionAop(ComboTransaction comboTransaction) {
        this.comboTransaction = comboTransaction;
    }

    @Pointcut("within(com.tl.datasource.service.impl.*)")
    public void pointCut() {
    }

    // 环绕通知
    @Around("pointCut() && @annotation(multiTransactional)")
    public Object inMultiTransactions(ProceedingJoinPoint pjp, MultiTransactional multiTransactional) {
        return comboTransaction.inCombinedTx(() -> {
            try {
                System.out.println("【切面通知】" + pjp.getSignature().getName());
                return pjp.proceed();       //执行目标方法
            } catch (Throwable throwable) {
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException) throwable;
                }
                throw new RuntimeException(throwable);
            }
        }, multiTransactional.value());
    }



}
