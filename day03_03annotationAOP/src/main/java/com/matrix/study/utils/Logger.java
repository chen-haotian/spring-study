package com.matrix.study.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-07 12:50:20
 * @Description: TODO 用于记录日志的工具类
 * @Version: TODO
 */
@Component(value = "logger")
@Aspect(value = "") // 表示当前类是一个切面
public class Logger {

    /**
     * 设置切入点表达式
     */
    @Pointcut(value = "execution(* *..*.*(..))")
    public void defaultPointcut() {

    }

    /**
     * 前置通知
     */
    //@Before("defaultPointcut()")
    public void beforePrintLog() {
        System.out.println("前置通知Logger类的beforePrintLog方法开始记录日志!");
    }

    /**
     * 后置通知
     */
    //@AfterReturning("defaultPointcut()")
    public void afterReturningPrintLog() {
        System.out.println("后置通知Logger类的afterReturningPrintLog方法开始记录日志!");
    }

    /**
     * 异常通知
     */
    //@AfterThrowing("defaultPointcut()")
    public void afterThrowingPrintLog() {
        System.out.println("异常通知Logger类的afterThrowingPrintLog方法开始记录日志!");
    }

    /**
     * 最终通知
     */
    //@After("defaultPointcut()")
    public void afterPrintLog() {
        System.out.println("最终通知Logger类的afterPrintLog方法开始记录日志!");
    }

    /**
     * 环绕通知
     * @param proceedingJoinPoint 切入点方法调用接口
     * @return
     */
    @Around("defaultPointcut()")
    public Object aroundPrintLog(ProceedingJoinPoint proceedingJoinPoint) {
        Object rtValue = null;
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            System.out.println("环绕通知Logger类的aroundPrintLog方法开始记录日志!。。。前置通知");
            rtValue = proceedingJoinPoint.proceed(args);
            System.out.println("环绕通知Logger类的aroundPrintLog方法开始记录日志!。。。后置通知");
            return rtValue;
        } catch (Throwable e) {
            System.out.println("环绕通知Logger类的aroundPrintLog方法开始记录日志!。。。异常通知");
            throw new RuntimeException(e);
        } finally {
            System.out.println("环绕通知Logger类的aroundPrintLog方法开始记录日志!。。。最终通知");
        }
    }
}
