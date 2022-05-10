package com.matrix.study.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 19:44:42
 * @Description: TODO 事务管理工具类（开启事务、提交事务、回滚事务）
 * @Version: TODO
 */
@Component(value = "transactionManagerUtil")
@Aspect // 配置切面
public class TransactionManagerUtil {

    @Autowired
    @Qualifier(value = "connectionUtil")
    /** 获取连接对象 */
    private ConnectionUtil connectionUtil;

    /**
     * 配置切入点表达式
     */
    @Pointcut("execution(* com.matrix.study.service.impl.*.*(..))")
    public void pt() {

    }

    /**
     * 开启事务
     */
//    @Before(value = "pt()") // 前置通知
    public void beginTransaction() {
        try {
            connectionUtil.getThreadConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 提交事务
     */
//    @AfterReturning(value = "pt()") // 后置通知
    public void commit() {
        try {
            connectionUtil.getThreadConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 回滚事务
     */
//    @AfterThrowing(value = "pt()") // 异常通知
    public void rollback() {
        try {
            connectionUtil.getThreadConnection().rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 释放连接
     */
//    @After(value = "pt()") // 最终通知
    public void release() {
        try {
            connectionUtil.getThreadConnection().close(); // 将连接放回连接池中
            connectionUtil.removeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Around(value = "pt()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        Object rtValue = null;
        try {
            // 获取方法的参数
            Object[] args = proceedingJoinPoint.getArgs();
            // 前置通知
            this.beginTransaction();
            rtValue = proceedingJoinPoint.proceed(args);
            // 后置通知
            this.commit();
        } catch (Throwable throwable) {
            // 异常通知
            this.rollback();
            throw new RuntimeException(throwable);
        } finally {
            // 最终通知
            this.rollback();
        }
        return rtValue;
    }
}
