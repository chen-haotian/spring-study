package com.matrix.study.factory;

import com.matrix.study.service.IAccountService;
import com.matrix.study.utils.TransactionManagerUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 23:30:40
 * @Description: TODO 用于创建Service的代理对象的工厂
 * @Version: TODO
 */
public class BeanFactory {

    private IAccountService accountService;

    private TransactionManagerUtil transactionManagementUtil;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    public final void setTransactionManagerUtil(TransactionManagerUtil transactionManagementUtil) {
        this.transactionManagementUtil = transactionManagementUtil;
    }

    /**
     * 获取IAccountService接口的代理对象
     * @return
     */
    public IAccountService getAccountService() {
        IAccountService proxyIAccountService = (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object rtValue = null;
                try {
                    // 1.开启事务
                    transactionManagementUtil.beginTransaction();
                    // 2.执行操作
                    rtValue = method.invoke(accountService, args);
                    // 3.提交事务
                    transactionManagementUtil.commit();
                    // 4.返回结果
                } catch (Exception e) {
                    // 5.事务回滚
                    transactionManagementUtil.rollback();
                    throw new RuntimeException(e);
                } finally {
                    // 6.释放资源
                    transactionManagementUtil.release();
                }
                return rtValue;
            }
        });
        return proxyIAccountService;
    }
}
