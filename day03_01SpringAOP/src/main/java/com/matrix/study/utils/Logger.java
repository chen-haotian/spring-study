package com.matrix.study.utils;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-07 12:50:20
 * @Description: TODO 用于记录日志的工具类
 * @Version: TODO
 */
public class Logger {

    /**
     * 用于打印日志，计划让其在切入点方法执行之前执行（切入点方法就是业务层方法）
     */
    public void printLog() {
        System.out.println("Logger类的printLog方法开始记录日志!");
    }
}
