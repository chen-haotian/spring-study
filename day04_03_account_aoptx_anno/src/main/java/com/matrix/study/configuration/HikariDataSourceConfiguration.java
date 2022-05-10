package com.matrix.study.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-10 18:54:29
 * @Description: TODO Hikari数据源配置类
 * @Version: TODO
 */
@PropertySource(value = {"classpath:database/mysql.properties"})
public class HikariDataSourceConfiguration {

    @Value(value = "${jdbc.driver}")
    private String driverClassName;

    @Value(value = "${jdbc.url}")
    private String url;

    @Value(value = "${jdbc.username}")
    private String username;

    @Value(value = "${jdbc.password}")
    private String password;

    @Bean(name = "hikariDataSource")
    @Scope(value = "singleton")
    public HikariDataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }
}
