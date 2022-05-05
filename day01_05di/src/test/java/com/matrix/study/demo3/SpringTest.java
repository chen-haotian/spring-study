package com.matrix.study.demo3;

import com.matrix.study.demo3.configuration.TestConfig;
import com.matrix.study.demo3.controller.TestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 16:27:58
 * @Description: TODO
 * @Version: TODO
 */
public class SpringTest {
    public static void main(String[] args) {
        // 1.读取spring配置文件创建bean容器
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
        // 2.根据bean的名称获取bean对象
        TestController testController = (TestController) applicationContext.getBean("testController");
        testController.info();
    }
}
