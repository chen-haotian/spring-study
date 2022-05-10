package com.matrix.study.dao;

import com.matrix.study.domain.Account;

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

    /**
     * 根据账户名称查询
     *
     * @param accountName 账户名称
     * @return 如果有唯一的一个结果就返回，如果没有结果就返回null
     *          如果结果集超过一个就抛出异常
     */
    Account findAccountByName(String accountName);
}
