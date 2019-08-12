package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.Dao.CartMapper;
import com.qf.Service.ICartService;
import com.qf.Service.IGoodsService;
import com.qf.entity.Goods;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/22 21:27
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    IGoodsService goodsService;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public int insertCart(User user, String cartToken, ShopCart shopCart) {
        //计算商品小计  通过数量价格
        Goods goods = goodsService.getGoodsByid(shopCart.getGid());
        Integer gnumber = shopCart.getGnumber();
        BigDecimal sprice = goods.getGprice().multiply(BigDecimal.valueOf(gnumber));
        shopCart.setSprice(sprice);
        //创建时间
        shopCart.setCreatetime(new Date());
        //判断是否登录 登录了加到数据库
        if(user!=null){
                //加到数据库中
                shopCart.setUid(user.getId());
                cartMapper.insert(shopCart);
        }else {
            //没登录放到redis 使用链表
            redisTemplate.opsForList().leftPush(cartToken,shopCart);
        }
        return 1;
    }

    @Override
    public List<ShopCart> queryCartlist(User user, String cartToken) {
        List<ShopCart> list=null;
        //判断用户是否登录
        if (user!=null){
            //从数据库查
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("uid",user.getId());
            list = cartMapper.selectList(queryWrapper);
        }else{
            if (cartToken!=null){
                Long size = redisTemplate.opsForList().size(cartToken);
                list=  redisTemplate.opsForList().range(cartToken,0,size);
            }
            //从redis查
        }
        //通过购物车对象 获得商品对象
        for (ShopCart shopCart : list) {
            Goods goodsByid = goodsService.getGoodsByid(shopCart.getGid());
            shopCart.setGoods(goodsByid);

        }
        return list;
    }

    @Override
    public int mergeCarts(String cartToken, User user) {
        System.out.println("整合cart"+user);
        if(cartToken!=null){
            Long size = redisTemplate.opsForList().size(cartToken);
            List<ShopCart> shopCarts = redisTemplate.opsForList().range(cartToken, 0, size);
            System.out.println("整合cart"+shopCarts);
            for (ShopCart shopCart : shopCarts) {
                shopCart.setUid(user.getId());
                cartMapper.insert(shopCart);
            }
            //清空购物车
            redisTemplate.delete(cartToken);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteCart(User user) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uid",user.getId());
        return  cartMapper.delete(queryWrapper);

    }
}
