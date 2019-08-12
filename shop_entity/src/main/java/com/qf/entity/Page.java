package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/15 20:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> implements Serializable {
    private Integer pageNum = 1; // 当前页

    private Integer pageSize = 3; // 每页显示的条数

    private Integer totalPage; // 总页数

    private Integer totalCount; // 总条数

    private List<T> list; // 当前页要展示的集合

    private String url; // 点击下一页的请求的地址

}
