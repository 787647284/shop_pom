package com.qf.Service;

import com.qf.entity.Goods;
import com.qf.entity.ShopCart;
import com.qf.entity.User;

import java.util.List;

public interface ICartService {
    int insertCart(User user, String cartToken, ShopCart shopCart);
    List<ShopCart> queryCartlist(User user,String cartToken);
    int  mergeCarts(String cartToken,User user);
    int deleteCart(User user);
}
