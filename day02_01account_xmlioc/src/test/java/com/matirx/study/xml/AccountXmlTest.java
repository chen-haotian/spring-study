package com.matirx.study.xml;

import com.matrix.study.anno.configuration.ScanConfiguration;
import com.matrix.study.xml.domain.Account;
import com.matrix.study.xml.service.IAccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 21:30:46
 * @Description: TODO 单元测试(账户crud基于xml的方式)
 * @Version: TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-xml/spring-beans.xml"})
public class AccountXmlTest {

    @Autowired
    private IAccountService accountService;

    /*
    @Before
    public void init() {
        // 1.读取Spring配置文件创建IOC容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-xml/spring-beans.xml");
        // 2.根据bean名称获取bean对象
        accountService = (IAccountService) applicationContext.getBean("accountService");
    }*/

    @Test
    public void testFindAll() {
        accountService.findAllAccount().forEach(System.out::println);
    }

    @Test
    public void testFindById() {
        // 2.根据bean名称获取bean对象
        Account account = accountService.findAccountById(3L);
        System.out.println(account);
    }

    @Test
    public void testSave() {
        Account account = new Account();
        account.setName("Matrix");
        account.setMoney(2000.0);
        accountService.saveAccount(account);
    }

    @Test
    public void testUpdate() {
        Account account = new Account();
        account.setId(4L);
        account.setName("Test");
        account.setMoney(3500.0);
        accountService.updateAccount(account);
    }

    @Test
    public void testDelete() {
        accountService.deleteAccount(4L);
    }
}
