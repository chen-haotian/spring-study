package com.matrix.study;

import com.matrix.study.hello.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-04 18:57:32
 * @Description: TODO 测试类
 * @Version: TODO
 */
public class SpringTest {
    public static void main(String[] args) {
        // 1.读取spring配置文件创建bean容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-beans.xml");
        // 2.根据bean的名称获取bean对象
        HelloWorld helloWorld = (HelloWorld) applicationContext.getBean("helloWorld");
        System.out.println(helloWorld);
        System.out.println(helloWorld.getMessage());
    }
}
