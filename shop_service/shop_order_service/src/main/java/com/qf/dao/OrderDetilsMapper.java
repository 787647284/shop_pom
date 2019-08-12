package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.OrderDetils;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/26 20:42
 */
public interface OrderDetilsMapper extends BaseMapper<OrderDetils> {
        //批量添加订单详情
        int insertDetils(@Param("orderDetils") List<OrderDetils> orderDetils,
                         @Param("tableIndex") int tableIndex);
}
