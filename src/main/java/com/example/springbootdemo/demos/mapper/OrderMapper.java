package com.example.springbootdemo.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootdemo.demos.entity.OrderDO;
import com.example.springbootdemo.demos.entity.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhangdi
 * @date 2024/9/5 9:58
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    List<OrderStatistics> getTotalCountByDept(@Param("firstDay") LocalDateTime firstDay, @Param("lastDay") LocalDateTime lastDay);

    List<OrderStatistics> getOverdueCountByDept(@Param("firstDay") LocalDateTime firstDay, @Param("lastDay") LocalDateTime lastDay);

    List<OrderStatistics> getOverdueCountByType(@Param("firstDay") LocalDateTime firstDay, @Param("lastDay") LocalDateTime lastDay);

    List<OrderStatistics> getTotalCountByType(@Param("firstDay") LocalDateTime firstDay, @Param("lastDay") LocalDateTime lastDay);
}