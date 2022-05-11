package com.matrix.study.service;

import com.matrix.study.domain.Account;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-11 17:54:35
 * @Description: TODO 账户业务层接口
 * @Version: TODO
 */
public interface IAccountService {

    /**
     * 根据账户id获取账户信息
     * @param id 账户id
     * @return
     */
    Account findByAccountById(Long id);

    /**
     * 转账
     *
     * @param sourceName 转出账户
     * @param targetName 转入账户
     * @param money 金额
     */
    void transfer(String sourceName, String targetName, Double money);
}
