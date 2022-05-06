package com.matrix.study.anno.service;

import com.matrix.study.anno.domain.Account;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 19:02:38
 * @Description: TODO 账户的业务层接口
 * @Version: TODO
 */
public interface IAccountService {

    /**
     * 查询所有账户数据
     *
     * @return 所有的账户数据
     */
    List<Account> findAllAccount();

    /**
     * 根据账户id获取账户信息
     *
     * @param id 账户id
     * @return 账户信息
     */
    Account findAccountById(Long id);

    /**
     * 保存账户信息
     *
     * @param account 账户信息
     */
    void saveAccount(Account account);

    /**
     * 更新账户信息
     *
     * @param account 账户信息
     */
    void updateAccount(Account account);

    /**
     * 根据账户id删除账户信息
     *
     * @param id 账户id
     */
    void deleteAccount(Long id);
}
