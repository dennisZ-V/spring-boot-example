package com.example.springbootdemo.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootdemo.demos.entity.*;

import java.util.List;

/**
 * @author zhangdi
 * @date 2024/9/5 9:59
 */
public interface OrderService extends IService<OrderDO> {

    /**
     * 分页查询
     * @param pageNum 当前页码
     * @param pageSize 页大小
     * @return PageResult
     */
    PageResult<OrderVO> search(int pageNum, int pageSize);

    /**
     * 工单新增
     * @param orderDto 工单数据传输对象
     * @return String
     */
    String saveOrder(OrderDTO orderDto);

    /**
     * 工单修改
     * @param orderDto 工单数据传输对象
     * @return String
     */
    String updateById(OrderDTO orderDto);

    /**
     * 分派工单
     * @param orderId 工单id
     * @param deptId 部门id
     * @return String
     */
    String dispatchOrder(int orderId, int deptId);

    /**
     * 统计
     * @param year 年
     * @param month 月
     * @param dimension 维度（dept/type）
     *                  以部门或者工单类型统计
     * @return List<OrderMetricsVO>
     */
    List<OrderMetricsVO> getOrderMetricsByDept(int year, int month, String dimension);
}
