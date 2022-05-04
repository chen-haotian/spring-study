package com.matrix.study.controller;

import com.matrix.study.factory.BeanFactory;
import com.matrix.study.service.IAccountService;
import com.matrix.study.service.impl.IAccountServiceImpl;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-04 00:29:31
 * @Description: TODO 模拟表现层
 * @Version: TODO
 */
public class AccountController {
    public static void main(String[] args) {
        // 表现层依赖业务层的接口
//        IAccountService accountService = new IAccountServiceImpl();
        // 通过工厂的方式获取IAccountService的实例化对象
        IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");
        accountService.saveAccount();
    }
}
