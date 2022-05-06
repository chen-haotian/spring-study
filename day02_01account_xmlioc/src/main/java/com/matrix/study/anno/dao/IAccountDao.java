package com.matrix.study.anno.dao;

import com.matrix.study.anno.domain.Account;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 19:04:50
 * @Description: TODO 账户持久层接口
 * @Version: TODO
 */
public interface IAccountDao {

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
