package com.example.springbootdemo.demos.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhangdi
 * @date 2024/9/5 10:51
 */
@Data
@Schema(description = "工单展示层对象")
public class OrderVO {
    @Schema(description = "主键")
    private Integer id;
    @Schema(description = "工单编号")
    private String orderNo;
    @Schema(description = "工单类型，0-交办，1-直接答复，2-无效工单")
    private int orderType;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "处理部门")
    private Integer handleDeptId;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "分派时间")
    private LocalDateTime fenpaiTime;
    @Schema(description = "是否超期。0-否，1-是")
    private byte isOverdue;
}
