package com.matrix.study.demo3.service.impl;

import com.matrix.study.demo3.dao.TestDao;
import com.matrix.study.demo3.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 16:24:39
 * @Description: TODO
 * @Version: TODO
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public void info() {
        testDao.info();
    }
}
