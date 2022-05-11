package com.matrix.study.dao;

import com.matrix.study.domain.Account;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-11 20:31:01
 * @Description: TODO 账户持久层接口
 * @Version: TODO
 */
public interface IAccountDao {

    /**
     * 根据账户id获取账户信息
     * @param id 账户ID
     * @return 账户信息Account
     */
    Account findAccountById(Long id);

    /**
     * 根据名称获取账户信息
     *
     * @param accountName 账户名称
     * @return 账户信息Account
     */
    Account findAccountByName(String accountName);

    /**
     * 修改账户信息
     *
     * @param account 账户对象
     */
    void updateAccount(Account account);
}
