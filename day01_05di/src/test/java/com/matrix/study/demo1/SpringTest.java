package com.matrix.study.demo1;

import com.matrix.study.demo1.hello.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 10:13:59
 * @Description: TODO 测试类：依赖注入方式一使用构造函数的方式注入
 * @Version: TODO
 */
public class SpringTest {
    public static void main(String[] args) {
        // 1.读取spring配置文件创建bean容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/demo1/spring-beans.xml");
        // 2.根据bean的名称获取bean对象
        HelloWorld helloWorld = (HelloWorld) applicationContext.getBean("helloWorld");
        System.out.println(helloWorld);
        helloWorld.printInfo();
    }
}
