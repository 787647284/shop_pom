package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Order;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/26 20:42
 */
public interface OrderMapper extends BaseMapper<Order> {
    //添加订单
    int insertOrder(@Param("order") Order order, @Param("tableIndex") int tableIndex);
    Order queryByOid(@Param("orderid") String orderid, @Param("tableIndex") int tableIndex);
    int updateOrderStatus(@Param("orderid")String orderid,@Param("status") int status,@Param("tableIndex") int tableIndex);
    //查询用户的所有订单
    List<Order> queryOrdersByUid(@Param("uid") Integer uid, @Param("tableIndex") int tableIndex);
}
