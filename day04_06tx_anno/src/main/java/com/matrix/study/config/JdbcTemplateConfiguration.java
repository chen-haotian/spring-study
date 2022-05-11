package com.matrix.study.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-11 23:49:13
 * @Description: TODO JdbcTemplate配置类
 * @Version: TODO
 */
@Configuration
@Import({HikariDataSourceConfiguration.class})
public class JdbcTemplateConfiguration {

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }
}
