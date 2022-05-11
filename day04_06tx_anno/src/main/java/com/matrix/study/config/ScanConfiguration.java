package com.matrix.study.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-11 23:33:17
 * @Description: TODO 配置类用于扫描创建SpringIOC容器
 * @Version: TODO
 */
@Configuration
@EnableAspectJAutoProxy // 开启AOP支持
@ComponentScan(basePackages = {"com.matrix.study"}) // 包扫描
public class ScanConfiguration {

}
