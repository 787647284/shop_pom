package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IAddressService;
import com.qf.Service.ICartService;
import com.qf.Service.IOrderService;
import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.Order;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/23 19:46
 */
@RequestMapping("/order")
@Controller
public class OrderController{
    @Reference
    ICartService cartService;
    //进入购物车
    @Reference
    IAddressService addressService;
    @Reference
    IOrderService orderService;
    @IsLogin
    @RequestMapping("/edit")
    public String edit(User user, Model model){
        //查询当前用户的所有购物车
        List<ShopCart> shopCarts = cartService.queryCartlist(user, null);
        //查询当前用户的所有地址
        List<Address> addresses = addressService.getAddressList(user);
        //查询当前用户购物车的总价
        BigDecimal bigDecimal=BigDecimal.valueOf(0.0);
        System.out.println("查询出的购物车"+shopCarts+"查询出的地址"+addresses);
        for (ShopCart ShopCart : shopCarts) {
            bigDecimal.add(ShopCart.getSprice());
        }
        model.addAttribute("carts", shopCarts);
        model.addAttribute("addresses", addresses);
        model.addAttribute("allprice", bigDecimal.doubleValue());
            return "orderedit";
    }
    /**
     * 下单
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/insertOrder")
    public String insertOrder(Integer aid, User user){
        //下单
        Order order = orderService.insertOoder(aid,user);
        System.out.println("order下的订单"+order);
        return "redirect:/pay/alipay?orderid="+order.getOrderid();
    }



    @IsLogin(mustLogin = true)
    @RequestMapping("/list")
    public String showList(User user, Model model){

        List<Order> orders = orderService.queryOrdersByUid(user.getId());
        model.addAttribute("orders", orders);

        return "orderlist";
    }

}
