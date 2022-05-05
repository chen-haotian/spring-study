package com.matrix.study.factory;

import com.matrix.study.hello.HelloWorld;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-04 22:05:29
 * @Description: TODO 生产HelloWorld实例的bean工厂
 * @Version: TODO
 */
public class HelloWorldFactory {

    /**
     * 获取HelloWorld实例
     *
     * @return 返回HelloWorld类的实例化对象
     */
    public HelloWorld getHelloWorld() {
        return new HelloWorld();
    }
}
