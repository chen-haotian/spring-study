package com.matrix.study.dao.impl;

import com.matrix.study.dao.IAccountDao;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-04 00:31:31
 * @Description: TODO 账户持久层实现类
 * @Version: TODO
 */
public class IAccountDaoImpl implements IAccountDao {
    @Override
    public void saveAccount() {
        System.out.println("模拟保存账户");
    }
}
