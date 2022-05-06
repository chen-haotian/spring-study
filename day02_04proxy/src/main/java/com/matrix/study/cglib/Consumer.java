package com.matrix.study.cglib;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 21:36:31
 * @Description: TODO 消费者
 * @Version: TODO
 */
public class Consumer {
    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.saleProduct(10000.0);

        // 创建代理对象
        Producer cglibProducer = (Producer) Enhancer.create(producer.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                // 提供方法增强
                Object rtValue = null;
                // 1.获取方法执行的参数
                Double money = (Double) args[0];
                // 2.判断执行的方法是不是销售操作
                if ("saleProduct".equals(method.getName())) {
                    rtValue = method.invoke(producer, money * 0.8);
                }
                return rtValue;
            }
        });
        cglibProducer.saleProduct(10000.0);
    }
}
