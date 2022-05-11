package com.matrix.study.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-11 23:36:11
 * @Description: TODO 用于配置事务管理器
 * @Version: TODO
 */
@Configuration
@EnableTransactionManagement // 开启事务支持
@Import({HikariDataSourceConfiguration.class})
public class TransactionManagementConfiguration {

    @Autowired
    @Qualifier(value = "hikariDataSource")
    private HikariDataSource hikariDataSource;

    @Bean("transactionManager")
    public DataSourceTransactionManager getTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(hikariDataSource);
        return transactionManager;
    }
}
