package com.matrix.study.dao.impl;

import com.matrix.study.configuration.QueryRunnerConfiguration;
import com.matrix.study.dao.IAccountDao;
import com.matrix.study.domain.Account;
import com.matrix.study.utils.ConnectionUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 19:05:11
 * @Description: TODO 账户持久层接口实现类
 * @Version: TODO
 */
@Repository(value = "accountDao")
public class AccountDaoImpl implements IAccountDao {

    // private QueryRunner queryRunner;

    @Autowired
    @Qualifier(value = "queryRunner")
    QueryRunner queryRunner;

    @Autowired
    @Qualifier(value = "connectionUtil")
    private ConnectionUtil connectionUtil;

    @Override
    public List<Account> findAllAccount() {
        try {
            return queryRunner.query(connectionUtil.getThreadConnection(), "select * from tb_account", new BeanListHandler<>(Account.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(Long id) {
        try {
            return queryRunner.query(connectionUtil.getThreadConnection(), "select * from tb_account where id = ?", new BeanHandler<>(Account.class), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {
        try {
            queryRunner.update(connectionUtil.getThreadConnection(), "insert into tb_account(id, name, money) values (default , ?, ?)", account.getName(), account.getMoney());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            queryRunner.update(connectionUtil.getThreadConnection(), "update tb_account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(), account.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try {
            queryRunner.update(connectionUtil.getThreadConnection(), "delete from tb_account where id = ?", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountByName(String accountName) {
        try {
            List<Account> accountList = queryRunner.query(connectionUtil.getThreadConnection(), "select * from tb_account where name = ?", new BeanListHandler<>(Account.class), accountName);
            if (accountList.size() == 0 || accountList == null) {
                return null;
            }
            if (accountList.size() > 1) {
                throw new RuntimeException("结果集不唯一，数据有问题");
            }
            return accountList.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
