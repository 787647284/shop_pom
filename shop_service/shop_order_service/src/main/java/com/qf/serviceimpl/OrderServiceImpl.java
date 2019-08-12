package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.Service.IAddressService;
import com.qf.Service.ICartService;
import com.qf.Service.IOrderService;
import com.qf.dao.OrderDetilsMapper;
import com.qf.dao.OrderMapper;
import com.qf.dataconfig.DynamicDataSource;
import com.qf.entity.*;
import com.qf.util.HttpUtil;
import com.qf.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/26 20:41
 */
@Service
public class OrderServiceImpl implements IOrderService {
    /**
     * 添加订单
     */
    //工具类
            @Autowired
     private OrderUtil orderUtil;
            @Reference
     private IAddressService addressService;

            @Reference
     private ICartService cartService;
            @Autowired
            private OrderDetilsMapper orderDetilsMapper;
            @Autowired
            //订单
    private OrderMapper orderMapper;
    @Override
    public Order insertOoder(Integer aid, User user) {
        //确定数据源
        //获得用户id
        //确定库，使用用户id后四位
        int uids = Integer.parseInt(orderUtil.getUid(user.getId()));
            int dbIndex=uids%2+1;  //2号库
        System.out.println("定位到库的id"+dbIndex);
        DynamicDataSource.set("orderdb"+dbIndex);
        //确定表 用户id的后4位/2 %2
        int tableIndex=uids/2%2+1;  //2号表
        System.out.println("定位到的表id"+tableIndex);
        //通过收货地址查询地址详情
        Address address = addressService.getAddressByid(aid);
        //查询该用户的所有购物车
        List<ShopCart> shopCarts = cartService.queryCartlist(user, null);
        //通过所有购物车计算总价
        BigDecimal bigDecimal = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : shopCarts) {
            bigDecimal = bigDecimal.add(shopCart.getSprice());
        }
        //手动创建订单的对象
        Order order=new Order(
            orderUtil.createOrderId(user.getId()),
            address.getPerson(),
            address.getAddress(),
            address.getPhone(),
             bigDecimal,
             new Date(),
                0,
                user.getId(),
                null
        );
        System.out.println("生成的订单号为"+order.getOrderid());
        //根据购物车列表生成订单详情
        int i=0;
        //储存订单详情
        List<OrderDetils> orderDetilsList=new ArrayList<>();
        for (ShopCart shopCart : shopCarts) {
            OrderDetils orderDetils=new OrderDetils(
                    null,
                    order.getOrderid(),
                    shopCart.getGid(),
                    shopCart.getGoods().getGname(),
                    shopCart.getGoods().getGprice(),
                    shopCart.getGoods().getGimage(),
                    shopCart.getGnumber(),
                    shopCart.getSprice()

            );
            orderDetilsList.add(orderDetils);
            i++;
            if (i%1000==0||i==shopCarts.size()){
                    //批量插入
                orderDetilsMapper.insertDetils(orderDetilsList,tableIndex);
                orderDetilsList.clear();
            }
        }
        System.out.println("order"+order+"tableIndex"+tableIndex);
            orderMapper.insertOrder(order,tableIndex);
            cartService.deleteCart(user);
        //清空购物车
        return order;
    }

    @Override
    public Order queryByOid(String orderid) {
        //确定数据源
        //获得用户id
        //确定库，使用用户id后四位
        int uids = orderUtil.parseOrderId(orderid);
        int dbIndex=uids%2+1;  //2号库
        System.out.println("定位到库的id"+dbIndex);
        DynamicDataSource.set("orderdb"+dbIndex);
        //确定表 用户id的后4位/2 %2
        int tableIndex=uids/2%2+1;  //2号表
        System.out.println("定位到的表id"+tableIndex);
        return orderMapper.queryByOid(orderid,tableIndex);
    }

    //修改状态
    @Override
    public int updateOrderStatus(String orderid, int status) {
        //确定数据源
        //获得用户id
        //确定库，使用用户id后四位
        int uids = orderUtil.parseOrderId(orderid);
        int dbIndex=uids%2+1;  //2号库
        System.out.println("定位到库的id"+dbIndex);
        DynamicDataSource.set("orderdb"+dbIndex);
        //确定表 用户id的后4位/2 %2
        int tableIndex=uids/2%2+1;  //2号表
        System.out.println("定位到的表id"+tableIndex);
        return orderMapper.updateOrderStatus(orderid,1,tableIndex);
    }
    @Override
    public List<Order> queryOrdersByUid(Integer uid) {
        //确定数据源
        //获得用户id
        //确定库，使用用户id后四位
        int uids = Integer.parseInt(OrderUtil.getUid(uid));
        int dbIndex=uids%2+1;  //2号库
        System.out.println("定位到库的id"+dbIndex);
        DynamicDataSource.set("orderdb"+dbIndex);
        //确定表 用户id的后4位/2 %2
        int tableIndex=uids/2%2+1;  //2号表
        System.out.println("定位到的表id"+tableIndex);
       return orderMapper.queryOrdersByUid(uid,tableIndex);
    }

}
