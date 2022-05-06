package com.matrix.study.anno.service.impl;

import com.matrix.study.anno.service.IAccountService;
import com.matrix.study.anno.dao.IAccountDao;
import com.matrix.study.anno.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 19:02:48
 * @Description: TODO 账户的业务层接口实现类
 * @Version: TODO
 */
@Service(value = "accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    @Qualifier(value = "accountDao")
    private IAccountDao accountDao;

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
