package com.matrix.study.anno.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-05 20:49:11
 * @Description: TODO 账户实体
 * @Version: TODO
 */
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -3564169976835948442L;

    private Long id;

    private String name;

    private Double money;
}
