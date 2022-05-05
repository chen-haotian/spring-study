package com.matrix.study.demo1.hello;

import java.util.*;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 10:08:03
 * @Description: TODO 依赖注入方式一：构造注入
 * @Version: TODO
 */
public class HelloWorld {

    private String name;
    private Integer age;
    private Date date;

    /** 复杂的数据类型 */
    private String[] stringArray;

    private List<String> stringList;

    private Map<String, String> stringMap;

    private Set<String> stringSet;

    private Properties properties;

    public HelloWorld(String name, Integer age, Date date, String[] stringArray, List<String> stringList, Map<String, String> stringMap, Set<String> stringSet, Properties properties) {
        this.name = name;
        this.age = age;
        this.date = date;
        this.stringArray = stringArray;
        this.stringList = stringList;
        this.stringMap = stringMap;
        this.stringSet = stringSet;
        this.properties = properties;
    }

    public void printInfo() {
        System.out.println("name=" + name + ", age=" + age + ", date=" + date);
        System.out.println("array=" + Arrays.toString(stringArray));
        System.out.println("list=" + stringList.toString());
        System.out.println("map=" + stringMap.toString());
        System.out.println("set=" + stringSet.toString());
        System.out.println("properties=" + properties.toString());
    }
}
