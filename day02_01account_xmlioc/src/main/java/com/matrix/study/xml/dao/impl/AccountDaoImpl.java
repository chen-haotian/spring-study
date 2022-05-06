package com.matrix.study.xml.dao.impl;

import com.matrix.study.xml.domain.Account;
import com.matrix.study.xml.dao.IAccountDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 19:05:11
 * @Description: TODO 账户持久层接口实现类
 * @Version: TODO
 */
@Repository
public class AccountDaoImpl implements IAccountDao {

    private QueryRunner queryRunner;

    /**
     * set方法注入QueryRunner
     * @param queryRunner QueryRunner对象
     */
    public void setQueryRunner(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    @Override
    public List<Account> findAllAccount() {
        try {
            return queryRunner.query("select * from tb_account", new BeanListHandler<>(Account.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(Long id) {
        try {
            return queryRunner.query("select * from tb_account where id = ?", new BeanHandler<>(Account.class), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {
        try {
            queryRunner.update("insert into tb_account(id, name, money) values (default , ?, ?)", account.getName(), account.getMoney());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            queryRunner.update("update tb_account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(), account.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try {
            queryRunner.update("delete from tb_account where id = ?", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
