package com.matrix.study.service.impl;

import com.matrix.study.dao.IAccountDao;
import com.matrix.study.domain.Account;
import com.matrix.study.service.IAccountService;
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

    @Override
    public void transfer(String sourceName, String targetName, Double money) {
        // 1.根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        // 2.根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        // 3.转出账户减钱
        source.setMoney(source.getMoney() - money);
        // 4.转入账户加钱
        target.setMoney(target.getMoney() + money);
        // 5.更新转出账户
        accountDao.updateAccount(source);
        // 模拟异常
        int ext = 10 / 0;
        // 6.更新转入账户
        accountDao.updateAccount(target);
    }
}
