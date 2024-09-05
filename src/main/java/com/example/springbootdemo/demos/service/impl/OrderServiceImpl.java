package com.example.springbootdemo.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdemo.demos.OrderTypeEnum;
import com.example.springbootdemo.demos.entity.*;
import com.example.springbootdemo.demos.mapper.OrderMapper;
import com.example.springbootdemo.demos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangdi
 * @date 2024/9/5 9:59
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public PageResult<OrderVO> search(int pageNum, int pageSize) {
        Page<OrderDO> page = new Page<>(pageNum, pageSize);
        Page<OrderDO> orderDoPage = orderMapper.selectPage(page, null);
        List<OrderVO> orderVoList = orderDoPage.getRecords().stream()
                .map(OrderDO::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(orderVoList, orderDoPage.getTotal(), orderDoPage.getPages(), orderDoPage.getCurrent());
    }

    @Override
    public String saveOrder(OrderDTO orderDto) {
        OrderDO orderDo = orderDto.convertToDo();
        boolean exists = checkOrderNoExists(orderDo);
        if (exists) {
            return orderDo.getOrderNo() + " already exists.";
        }
        orderMapper.insert(orderDo);
        return "Save success.";
    }

    @Override
    public String updateById(OrderDTO orderDto) {
        OrderDO orderDo = orderDto.convertToDo();
        boolean exists = checkOrderNoExists(orderDo);
        if (exists) {
            return orderDo.getOrderNo() + " already exists.";
        }
        orderMapper.updateById(orderDo);
        return "Update Success.";
    }

    @Override
    public String dispatchOrder(int orderId, int deptId) {
        UpdateWrapper<OrderDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", orderId);
        updateWrapper.set("handle_dept_id", deptId);
        updateWrapper.set("fenpai_time", LocalDateTime.now());
        orderMapper.update(updateWrapper);
        return "Dispatch success.";
    }

    @Override
    public List<OrderMetricsVO> getOrderMetricsByDept(int year, int month, String dimension) {
        if (year <= Year.now().getValue() && month > 0 && month <= 12) {
            LocalDateTime firstDay = getFirstDayOfMonth(year, month);
            LocalDateTime lastDay = getLastDayOfMonth(year, month);
            List<OrderStatistics> overdueCountByDept;
            List<OrderStatistics> totalCountByDept;
            if ("dept".equals(dimension)) {
                overdueCountByDept = orderMapper.getOverdueCountByDept(firstDay, lastDay);
                totalCountByDept = orderMapper.getTotalCountByDept(firstDay, lastDay);

                Map<String, Long> totalCountMap = totalCountByDept.stream()
                        .collect(Collectors.toMap(OrderStatistics::getDeptName, OrderStatistics::getCount));

                return overdueCountByDept.stream()
                        .map(overdue -> {
                            Long totalOrderCount = totalCountMap.getOrDefault(overdue.getDeptName(), 0L);
                            double overdueRate = totalOrderCount == 0 ? 0.0 : (double) overdue.getCount() / totalOrderCount * 100;
                            return new OrderMetricsVO(year, month, null, overdue.getDeptName(), totalOrderCount, (int) overdueRate);
                        }).collect(Collectors.toList());
            } else {
                overdueCountByDept = orderMapper.getOverdueCountByType(firstDay, lastDay);
                totalCountByDept = orderMapper.getTotalCountByType(firstDay, lastDay);

                Map<Integer, Long> totalCountMap = totalCountByDept.stream()
                        .collect(Collectors.toMap(OrderStatistics::getOrderType, OrderStatistics::getCount));

                return overdueCountByDept.stream()
                        .map(overdue -> {
                            Long totalOrderCount = totalCountMap.getOrDefault(overdue.getOrderType(), 0L);
                            double overdueRate = totalOrderCount == 0 ? 0.0 : (double) overdue.getCount() / totalOrderCount * 100;
                            return new OrderMetricsVO(year, month, OrderTypeEnum.getTypeNameByCode(overdue.getOrderType()), null, totalOrderCount, (int) overdueRate);
                        }).collect(Collectors.toList());
            }
        } else {
            throw new IllegalArgumentException("Invalid year or month");
        }
    }

    public static LocalDateTime getFirstDayOfMonth(int year, int month) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        return LocalDateTime.of(firstDay, LocalTime.MIN);
    }

    private LocalDateTime getLastDayOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate lastDay = yearMonth.atEndOfMonth();
        return LocalDateTime.of(lastDay, LocalTime.MAX);
    }

    private boolean checkOrderNoExists(OrderDO orderDo) {
        QueryWrapper<OrderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderDo.getOrderNo());
        if (orderDo.getId() != null) {
            queryWrapper.ne("id", orderDo.getId());
        }
        Long count = orderMapper.selectCount(queryWrapper);
        return count > 0;
    }

}
