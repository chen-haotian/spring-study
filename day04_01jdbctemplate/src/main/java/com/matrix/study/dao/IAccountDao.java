package com.matrix.study.dao;

import com.matrix.study.domain.Account;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-10 10:40:15
 * @Description: TODO
 * @Version: TODO
 */
public interface IAccountDao {

    /**
     *  根据 id 查询账户信息
     * @param id 账户id
     * @return 账户信息
     */
    Account selectAccountById(Long id);

    /**
     * 根据 name 查询账户信息
     * @param name 账户名称
     * @return 账户信息
     */
    Account selectAccountByName(String name);
}
