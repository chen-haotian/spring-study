package com.matrix.study.service.impl;

import com.matrix.study.dao.IAccountDao;
import com.matrix.study.dao.impl.IAccountDaoImpl;
import com.matrix.study.factory.BeanFactory;
import com.matrix.study.service.IAccountService;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-04 00:33:27
 * @Description: TODO
 * @Version: TODO
 */
public class IAccountServiceImpl implements IAccountService {

    // 业务层依赖持久层
//    private IAccountDao accountDao = new IAccountDaoImpl();

    // 工厂的方式获取IAccountDao实例化对象
    private IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao");

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
