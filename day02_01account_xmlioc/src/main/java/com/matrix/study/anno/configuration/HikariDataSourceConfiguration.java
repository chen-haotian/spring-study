package com.matrix.study.anno.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 01:14:33
 * @Description: TODO 配置Hikari数据源配置类
 * @Version: TODO
 */
@PropertySource(value = "classpath:spring-anno/mysql.properties")
public class HikariDataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean(name = "hikariDataSource")
    @Scope(value = "singleton")
    public HikariDataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        // 设置数据源信息
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }
}
