package com.matrix.study.hello;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-04 21:38:41
 * @Description: TODO bean的生命周期和作用范围
 * @Version: TODO
 */
public class HelloWorld {

    /*
    public HelloWorld(String message, String name) {
        this.message = message;
    }*/

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
