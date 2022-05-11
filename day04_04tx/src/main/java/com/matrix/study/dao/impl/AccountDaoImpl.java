package com.matrix.study.dao.impl;

import com.matrix.study.dao.IAccountDao;
import com.matrix.study.domain.Account;
import com.matrix.study.mapper.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-11 20:31:34
 * @Description: TODO 账户持久层接口
 * @Version: TODO
 */
public class AccountDaoImpl implements IAccountDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account findAccountById(Long id) {
        List<Account> accounts = jdbcTemplate.query("select id, name, money from tb_account where id = ?", new AccountRowMapper(), id);
        if(accounts.isEmpty()) {
            throw new RuntimeException("id对应的数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account findAccountByName(String accountName) {
        List<Account> accounts = jdbcTemplate.query("select id, name, money from tb_account where name = ?", new AccountRowMapper(), accountName);
        if(accounts.isEmpty()) {
            throw new RuntimeException("id对应的数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public void updateAccount(Account account) {
        jdbcTemplate.update("update tb_account set name = ?, money = ? where  id = ?", account.getName(), account.getMoney(), account.getId());
    }
}
