package com.matrix.study;

import com.matrix.study.config.ScanConfiguration;
import com.matrix.study.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-11 21:04:51
 * @Description: TODO 测试类
 * @Version: TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= ScanConfiguration.class)
public class SpringTest {

    @Autowired
    private IAccountService accountService;

    @Test
    public void transfer() {
        accountService.transfer("张三", "李四", 500.0);
    }
}
