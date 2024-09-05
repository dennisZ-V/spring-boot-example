package com.example.springbootdemo.demos.controller;

import com.example.springbootdemo.demos.entity.OrderDTO;
import com.example.springbootdemo.demos.entity.OrderMetricsVO;
import com.example.springbootdemo.demos.entity.OrderVO;
import com.example.springbootdemo.demos.entity.PageResult;
import com.example.springbootdemo.demos.service.OrderService;
import com.example.springbootdemo.demos.util.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangdi
 * @date 2024/9/5 10:11
 * @description 工单业务接口
 */
@RestController
@RequestMapping("/order")
@Tag(name = "工单接口")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 分页查询接口
     * @param pageNum 当前页码
     * @param pageSize 页大小
     * @return PageResult
     */
    @GetMapping("/search")
    @Operation(summary = "分页查询接口")
    @Parameters(value = {
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "页大小", in = ParameterIn.QUERY)
    })
    public PageResult<OrderVO> search(@RequestParam int pageNum, @RequestParam int pageSize) {
        return orderService.search(pageNum, pageSize);
    }

    /**
     * 工单新增接口
     * @param orderDto 工单数据传输对象
     * @return String
     */
    @PostMapping("/save")
    @Operation(summary = "工单新增接口")
    public String save(@RequestBody OrderDTO orderDto) {
        try {
            ValidationUtils.validate(orderDto);
            return orderService.saveOrder(orderDto);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return e.getMessage();
        }
    }

    /**
     * 工单修改接口
     * @param orderDto 工单数据传输对象
     * @return String
     */
    @PutMapping("/update")
    @Operation(summary = "工单修改接口")
    public String updateById(@RequestBody OrderDTO orderDto) {
        try {
            ValidationUtils.validate(orderDto);
            return orderService.updateById(orderDto);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return e.getMessage();
        }
    }

    /**
     * 工单删除接口
     * @param ids 工单id集合
     * @return String
     */
    @DeleteMapping("/delete")
    @Operation(summary = "工单删除接口")
    public String deleteById(@RequestParam List<Integer> ids) {
        boolean res = orderService.removeBatchByIds(ids);
        return res ? "Delete success." : "Delete fail.";
    }

    /**
     * 分派工单接口
     * @param orderId 工单id
     * @param deptId 部门id
     * @return String
     */
    @PostMapping("/fenpai/{orderId}/{deptId}")
    @Operation(summary = "分派工单接口")
    public String dispatchOrder(@PathVariable int orderId, @PathVariable int deptId) {
        return orderService.dispatchOrder(orderId, deptId);
    }

    /**
     * 统计接口
     * @param year 年
     * @param month 月
     * @param dimension 维度（dept/type）
     *                  以部门或者工单类型统计
     * @return List<OrderMetricsVO>
     */
    @GetMapping("/getOrderMetrics/{dimension}")
    @Operation(summary = "统计接口")
    @Parameters(value = {
            @Parameter(name = "year", description = "年份", in = ParameterIn.QUERY),
            @Parameter(name = "month", description = "月份", in = ParameterIn.QUERY),
            @Parameter(name = "dimension", description = "维度：dept/type", in = ParameterIn.PATH)
    })
    public List<OrderMetricsVO> getOrderMetricsByDept(@RequestParam int year, @RequestParam int month, @PathVariable String dimension) {
        return orderService.getOrderMetricsByDept(year, month, dimension);
    }
}
