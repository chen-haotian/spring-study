package com.matrix.study.utils;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 19:34:29
 * @Description: TODO 连接的工具类，它用于从数据源中获取一个连接，并且实现和线程的绑定
 * @Version: TODO
 */
public class ConnectionUtil {

    /** 用于存放Connection对象 */
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    /** Hikari数据源 */
    private HikariDataSource hikariDataSource;

    /**
     * set方式转入Hikari数据源
     *
     * @param hikariDataSource Hikari数据源
     */
    public void setHikariDataSource(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    /**
     * 用于获取当前线程上的Connection对象
     * @return 当前线程上的Connection对象
     */
    public Connection getThreadConnection() {
        // 1.先从ThreadLocal中获取
        Connection connection = connectionThreadLocal.get();
        try {
            // 2.判断当前线程是否有连接
            if (connection == null) {
                // 3.从数据源中获取一个连接，并且存入ThreadLocal中
                connection = hikariDataSource.getConnection();
                connectionThreadLocal.set(connection);
            }
            // 4.返回当前线程上的连接
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将当前connection对象与当前线程解绑
     */
    public void removeConnection() {
        connectionThreadLocal.remove();
    }
}
