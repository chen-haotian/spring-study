package com.matrix.study.proxy;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 21:31:51
 * @Description: TODO 一个生产者
 * @Version: TODO
 */
public class Producer implements IProducer {

    /**
     * 销售
     * @param money 金额
     */
    public void saleProduct(Double money) {
        System.out.println("销售产品，并拿到钱:" + money);
    }

    /**
     * 售后
     * @param money 金额
     */
    public void afterService(Double money) {
        System.out.println("提供售后服务，并拿到钱:" + money);
    }
}
