package com.matrix.study.utils;

import java.sql.SQLException;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 19:44:42
 * @Description: TODO 事务管理工具类（开启事务、提交事务、回滚事务）
 * @Version: TODO
 */
public class TransactionManagerUtil {

    /** 获取连接对象 */
    private ConnectionUtil connectionUtil;

    /**
     * set方法注入ConnectionUtil
     * @param connectionUtil 连接管理工具
     */
    public void setConnectionUtil(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    /**
     * 开启事务
     */
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
    public void release() {
        try {
            connectionUtil.getThreadConnection().close(); // 将连接放回连接池中
            connectionUtil.removeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
