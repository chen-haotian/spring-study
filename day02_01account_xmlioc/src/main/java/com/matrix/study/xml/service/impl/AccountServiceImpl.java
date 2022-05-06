package com.matrix.study.xml.service.impl;

import com.matrix.study.xml.dao.IAccountDao;
import com.matrix.study.xml.domain.Account;
import com.matrix.study.xml.service.IAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 19:02:48
 * @Description: TODO 账户的业务层接口实现类
 * @Version: TODO
 */
@Service
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    /**
     * 使用set方法注入IAccountDao
     * @param accountDao 账户持久层接口
     */
    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Long id) {
        return accountDao.findAccountById(id);
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountDao.deleteAccount(id);
    }
}
