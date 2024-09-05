package com.example.springbootdemo.demos.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author zhangdi
 * @date 2024/9/5 9:52
 */
@Data
@TableName("t_order")
public class OrderDO {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String orderNo;

    private Integer orderType;

    private String title;

    private String content;

    private Integer handleDeptId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private LocalDateTime fenpaiTime;

    private Byte isOverdue;

    @TableLogic
    private Byte isRemoved;

    public static OrderVO convertToVO(OrderDO orderDO) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderDO, orderVO);
        return orderVO;
    }
}
