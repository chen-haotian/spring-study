package com.matrix.study.anno.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 00:56:54
 * @Description: TODO QueryRunner配置类
 * @Version: TODO
 */
@Configuration
@Import(value = {HikariDataSourceConfiguration.class})
public class QueryRunnerConfiguration {

    @Autowired
    @Qualifier(value = "hikariDataSource")
    private HikariDataSource hikariDataSource;

    /**
     * 设置数据源信息
     * @return 获取到QueryRunner的bean对象
     */
    @Bean(name = "queryRunner")
    @Scope(value = "prototype")
    public QueryRunner getQueryRunner() {
        return new QueryRunner(hikariDataSource);
    }
}
