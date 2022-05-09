package com.matrix.study.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-09 09:55:19
 * @Description: TODO 配置类用于扫描配置bean的注解
 * @Version: TODO
 */
@Configuration
@EnableAspectJAutoProxy // 开启AOP的支持
@ComponentScan(basePackages = "com.matrix.study")
public class ScanConfiguration {
}
