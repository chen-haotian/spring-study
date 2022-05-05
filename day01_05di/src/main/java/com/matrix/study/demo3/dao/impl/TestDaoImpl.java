package com.matrix.study.demo3.dao.impl;

import com.matrix.study.demo3.dao.TestDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 16:26:17
 * @Description: TODO
 * @Version: TODO
 */
@Repository
public class TestDaoImpl implements TestDao {
    @Override
    public void info() {
        System.out.println("测试。。");
    }
}
