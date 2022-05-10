package com.matrix.study.test;

import com.matrix.study.dao.IAccountDao;
import com.matrix.study.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-10 00:26:11
 * @Description: TODO
 * @Version: TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-beans.xml")
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IAccountDao accountDao;

    @Test
    public void saveTest() {
        jdbcTemplate.execute("insert into tb_account(name, money) values ('测试用户', 3000)");
    }

    @Test
    public void updateTest() {
        jdbcTemplate.update("update tb_account set name=?, money=? where id=?", "Matrix", 2000, 4L);
    }

    @Test
    public void deleteTest() {
        jdbcTemplate.update("delete from tb_account where id = ?", 4);
    }

    @Test
    public void finAllTest() {
        List<Account> accounts = jdbcTemplate.query("select * from tb_account", new AccountRowMapper(), null);
        accounts.forEach(System.out::println);
    }

    /**
     * 定义Account的封装策略
     */
    public class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setName(rs.getString("name"));
            account.setMoney(rs.getDouble("money"));
            return account;
        }
    }

    @Test
    public void findByIdTest() {
        List<Account> accounts = jdbcTemplate.query("select * from tb_account where id = ?", new AccountRowMapper(), 1);
        System.out.println(accounts.isEmpty()?"没有数据":accounts.get(0));
    }

    @Test
    public void countTest() {
        Long count = jdbcTemplate.queryForObject("select count(id) from tb_account where money > ?", Long.class, 1000);
        System.out.println(count);
    }

    @Test
    public void selectAccountByIdTest() {
        Account account = accountDao.selectAccountById(1L);
        System.out.println(account);
    }

    @Test
    public void selectAccountByName() {
        Account account = accountDao.selectAccountByName("李四");
        System.out.println(account);
    }
}
