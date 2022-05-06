package com.matrix.study.anno.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 00:44:55
 * @Description: TODO 配置类用于扫描配置bean的注解
 * @Version: TODO
 */
@Configuration
@ComponentScan(basePackages = "com.matrix.study.anno")
public class ScanConfiguration {

}
