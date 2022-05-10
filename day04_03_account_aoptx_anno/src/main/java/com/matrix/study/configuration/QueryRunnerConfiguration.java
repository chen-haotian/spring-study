package com.matrix.study.configuration;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-10 19:04:57
 * @Description: TODO QueryRunner配置类
 * @Version: TODO
 */
@Component
public class QueryRunnerConfiguration {

    @Bean(name = "queryRunner")
    @Scope(value = "prototype")
    public QueryRunner getQueryRunner() {
        return new QueryRunner();
    }
}
