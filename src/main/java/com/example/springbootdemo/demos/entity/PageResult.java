package com.example.springbootdemo.demos.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author zhangdi
 * @date 2024/9/5 10:48
 */
@Data
public class PageResult<T> {

    /**
     * 当前页的数据
     */
    private List<T> records;
    /**
     * 总记录数
     */
    @Schema(description = "总数")
    private long total;
    /**
     * 总页数
     */
    @Schema(description = "页数")
    private long pages;
    /**
     * 当前页码
     */
    @Schema(description = "当前页码")
    private long current;

    public PageResult(List<T> records, long total, long pages, long current) {
        this.records = records;
        this.total = total;
        this.pages = pages;
        this.current = current;
    }
}
