package com.example.springbootdemo.demos.entity;

import lombok.Data;

/**
 * @author zhangdi
 * @date 2024/9/5 14:36
 */
@Data
public class OrderStatistics {

    private Integer orderType;

    private String deptName;

    private Long count;
}
