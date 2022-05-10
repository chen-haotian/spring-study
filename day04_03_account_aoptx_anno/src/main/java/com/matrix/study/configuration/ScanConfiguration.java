package com.matrix.study.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-10 18:48:10
 * @Description: TODO
 * @Version: TODO
 */
@Configuration
@EnableAspectJAutoProxy // 开启SpringAOP支持
@ComponentScan(basePackages = {"com.matrix.study"})
public class ScanConfiguration {

}
