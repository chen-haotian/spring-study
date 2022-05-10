package com.matrix.study.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Matrix
 * @CreateDate: 2022-05-09 21:29:12
 * @Description: TODO
 * @Version: TODO
 */
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -3564169976835948442L;

    private Long id;

    private String name;

    private Double money;
}
