package com.example.springbootdemo.demos.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhangdi
 * @date 2024/9/5 13:49
 */
@Data
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrderMetricsVO {

    private Integer year;

    private Integer month;

    private String orderTypeName;

    private String deptName;

    private Long totalOrderCount;

    private Integer overdueRate;
}
