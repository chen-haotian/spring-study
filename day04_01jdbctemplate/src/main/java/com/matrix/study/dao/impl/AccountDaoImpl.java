package com.matrix.study.dao.impl;

import com.matrix.study.dao.IAccountDao;
import com.matrix.study.domain.Account;
import com.matrix.study.mapper.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-10 10:43:54
 * @Description: TODO
 * @Version: TODO
 */
public class AccountDaoImpl implements IAccountDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account selectAccountById(Long id) {
        List<Account> accounts = jdbcTemplate.query("select * from tb_account where id = ?", new AccountRowMapper(), id);
        if (accounts.isEmpty()) {
            throw new RuntimeException("数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account selectAccountByName(String name) {
        List<Account> accounts = jdbcTemplate.query("select * from tb_account where name = ?", new AccountRowMapper(), name);
        if (accounts.isEmpty()) {
            throw new RuntimeException("数据不存在");
        } else {
            return accounts.get(0);
        }
    }
}
