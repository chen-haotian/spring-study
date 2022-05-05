package com.matrix.study.demo2.hello;

import java.util.*;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 12:59:25
 * @Description: TODO 依赖注入使用set方法注入
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setStringMap(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }

    public void setStringSet(Set<String> stringSet) {
        this.stringSet = stringSet;
    }

    public void setProperties(Properties properties) {
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
