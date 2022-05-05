package com.matrix.study.factory;

import com.matrix.study.hello.HelloWorld;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-04 22:32:22
 * @Description: TODO 生产HelloWorld实例的bean静态工厂
 * @Version: TODO
 */
public class HelloWorldStaticFactory {

    /**
     * 获取HelloWorld实例
     *
     * @return 获取HelloWorld类的实例化对象
     */
    public static HelloWorld getStaticHelloWorld() {
        return new HelloWorld();
    }
}
