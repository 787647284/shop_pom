package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.Service.ICartService;
import com.qf.aop.IsLogin;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * linzebin
 *
 * 时间2019/7/21 19:34
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference
    ICartService service;
    /**
     * 自定义注解加aop实现添加购物车时  是否需要进行登录的拦截
     */

@IsLogin
@RequestMapping("/insert")
public String addCart (ShopCart shopCart, User user, @CookieValue(name = "cartToken",required = false) String cartToken, HttpServletResponse response){
    //购物车对象不存在
    if(cartToken==null){
     String  uuid= UUID.randomUUID().toString();
        Cookie cookie=new Cookie("cartToken",uuid);
        cookie.setMaxAge(60*60*24*365);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
        //将商品添加的购物车中
    int i = service.insertCart(user, cartToken, shopCart);
    return "succ";
}
@RequestMapping("list")
@ResponseBody
@IsLogin
    public String list(@CookieValue(name ="cartToken",required = false) String cartToken,User user,String callback){
    List<ShopCart> shopCarts = service.queryCartlist(user, cartToken);
    System.out.println("查询出来的购物车集合为"+shopCarts);
    return callback != null ? callback + "(" + JSON.toJSONString(shopCarts) + ")" : JSON.toJSONString(shopCarts);
}
//进入购物车
        @RequestMapping("/showlist")
        @IsLogin
    public String showList(User user, @CookieValue(name = "cartToken",required = false)String cartToken, Model model){
            List<ShopCart> shopCarts = service.queryCartlist(user, cartToken);
            BigDecimal bigDecimal=BigDecimal.valueOf(0.0);
            for (ShopCart shopCart : shopCarts) {
                bigDecimal= bigDecimal.add(shopCart.getSprice());
            }
            model.addAttribute("carts",shopCarts);
            model.addAttribute("allprice",bigDecimal.doubleValue());
            System.out.println("购物车价格"+shopCarts);
            System.out.println("总价"+bigDecimal.doubleValue());
            return "cartlist";
        }
}
