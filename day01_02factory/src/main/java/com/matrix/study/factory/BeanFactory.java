package com.matrix.study.factory;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-04 11:26:03
 * @Description: TODO 创建bean对象的一个工厂
 * @Version: TODO
 */
public class BeanFactory {

    /** properties对象 */
    private static Properties properties;

    /** beans对象用于存储bean */
    private static Map<String, Object> beans;

    static {
        try {
            properties = new Properties();
            beans = new HashMap<String, Object>();
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("beans.properties"));
            // 获取所有的key
            Enumeration<Object> keys = properties.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement().toString();
                Object beanPath = properties.get(key);
                // 根据获取出来的value去实例化对象
                Object instance = Class.forName(beanPath.toString()).newInstance();
                // 将beanName作为key和实例化对象作为value存入map容器中
                beans.put(key, instance);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    /**
     * 通过bean的名称获取bean
     * @param beanName bean的名称
     * @return 返回一个bean对象
     */
    public static Object getBean(String beanName) {
        return beans.get(beanName);
    }
}
