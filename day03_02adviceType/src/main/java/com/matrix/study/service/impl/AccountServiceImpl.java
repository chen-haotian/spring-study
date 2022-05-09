package com.matrix.study.service.impl;

import com.matrix.study.service.IAccountService;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-07 12:45:22
 * @Description: TODO
 * @Version: TODO
 */
public class AccountServiceImpl implements IAccountService {

    @Override
    public void saveAccount() {
        System.out.println("执行了保存操作");
        // 模拟业务异常
        int i = 10 / 0;
    }

    @Override
    public void updateAccount(int i) {
        System.out.println("执行了更新操作" + i);
    }

    @Override
    public int deleteAccount() {
        System.out.println("执行了删除操作");
        return 0;
    }
}
