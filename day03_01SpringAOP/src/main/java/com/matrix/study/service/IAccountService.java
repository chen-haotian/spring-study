package com.matrix.study.service;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-07 12:42:37
 * @Description: TODO 账户业务层接口
 * @Version: TODO
 */
public interface IAccountService {



    /**
     * 模拟保存账户
     */
    void saveAccount();

    /**
     * 模拟更新用户
     * @param i
     */
    void updateAccount(int i);

    /**
     * 模拟删除账户
     * @return
     */
    int deleteAccount();
}
