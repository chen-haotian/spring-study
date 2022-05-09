package com.matrix.study;

import com.matrix.study.configuration.ScanConfiguration;
import com.matrix.study.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-09 09:56:25
 * @Description: TODO
 * @Version: TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ScanConfiguration.class)
public class SpringTest {

    @Autowired
    private IAccountService accountService;

    @Test
    public void test1() {
        accountService.saveAccount();
    }
}
