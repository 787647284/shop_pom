package com.qf.Service;


import com.qf.entity.Order;
import com.qf.entity.User;

import java.util.List;

public interface IOrderService {
    Order insertOoder(Integer aid, User user);
    Order queryByOid(String  orderid);
    //修改商品状态
    int updateOrderStatus(String orderid,int status);
    //查询用户的所有订单
    List<Order> queryOrdersByUid( Integer uid);

}
