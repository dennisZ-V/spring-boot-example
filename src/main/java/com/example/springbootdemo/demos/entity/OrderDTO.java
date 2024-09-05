package com.example.springbootdemo.demos.entity;

import com.example.springbootdemo.demos.annotation.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author zhangdi
 * @date 2024/9/5 10:38
 */
@Data
@Schema(description = "工单传输对象")
public class OrderDTO {

    private Integer pageNum;

    private Integer pageSize;

    private Integer id;
    @NotEmpty
    @Schema(description = "工单编号")
    private String orderNo;
    @NotEmpty
    @Schema(description = "工单类型，0-交办，1-直接答复，2-无效工单")
    private Integer orderType;
    @NotEmpty
    @Schema(description = "标题")
    private String title;
    @NotEmpty
    @Schema(description = "内容")
    private String content;
    @Schema(description = "处理部门")
    private Integer handleDeptId;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "分派时间")
    private LocalDateTime fenpaiTime;
    @Schema(description = "是否超期。0-否，1-是")
    private Byte isOverdue;

    public OrderDO convertToDo() {
        OrderDO orderDo = new OrderDO();
        BeanUtils.copyProperties(this, orderDo);
        return orderDo;
    }
}
