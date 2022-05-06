package com.matrix.study.proxy;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-06 21:35:14
 * @Description: TODO
 * @Version: TODO
 */
public interface IProducer {

    /**
     * 销售
     * @param money 金额
     */
    public void saleProduct(Double money);

    /**
     * 售后
     * @param money 金额
     */
    public void afterService(Double money);
}
