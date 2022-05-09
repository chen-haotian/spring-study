package com.matrix.study;

import com.matrix.study.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-08 21:22:40
 * @Description: TODO 测试类
 * @Version: TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-beans.xml")
public class SpringTest {

    @Autowired
    private IAccountService accountService;

    @Test
    public void test1() {
        accountService.saveAccount();
        accountService.updateAccount(1);
        accountService.deleteAccount();
    }

    /**
     * 环绕通知
     */
    @Test
    public void test2() {
        accountService.saveAccount();
    }
}
