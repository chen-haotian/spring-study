package com.matrix.study.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 21:36:31
 * @Description: TODO 消费者
 * @Version: TODO
 */
public class Consumer {
    public static void main(String[] args) {
        final Producer producer = new Producer();
        producer.saleProduct(10000.0);

        // 代理对象
        IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(), producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 作用：执行被代理对象的任何接口方法都会经过该方法
                     * @param proxy 代理对象的引用
                     *
                     * @param method 当前执行的方法
                     *
                     * @param args 当前执行的方法所需要的参数
                     *
                     * @return 和被代理对象的方法有相同的返回值
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
        proxyProducer.saleProduct(10000.0);
    }
}
