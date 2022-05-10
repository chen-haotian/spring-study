package com.matrix.study.dao.impl;

import com.matrix.study.dao.IAccountDao;
import com.matrix.study.domain.Account;
import com.matrix.study.mapper.AccountRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-10 11:59:26
 * @Description: TODO
 * @Version: TODO
 */
public class AccountDaoImpl2 extends JdbcDaoSupport implements IAccountDao {

    @Override
    public Account selectAccountById(Long id) {
        List<Account> accounts = getJdbcTemplate().query("select * from tb_account where id = ?", new AccountRowMapper(), id);
        if(accounts.isEmpty()){
            throw new RuntimeException("数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account selectAccountByName(String name) {
        List<Account> accounts = getJdbcTemplate().query("select * from tb_account where name = ?", new AccountRowMapper(), name);
        if(accounts.isEmpty()){
            throw new RuntimeException("数据不存在");
        } else {
            return accounts.get(0);
        }
    }
}
