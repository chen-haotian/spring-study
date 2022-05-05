package com.matrix.study.demo3.controller;

import com.matrix.study.demo3.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 16:23:16
 * @Description: TODO
 * @Version: TODO
 */
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    public void info() {
        testService.info();
    }
}
