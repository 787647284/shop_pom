package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.Dao.GoodsMapper;
import com.qf.Service.IGoodsService;
import com.qf.Service.ISearchService;
import com.qf.entity.Goods;
import com.qf.util.HttpUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/5 15:32
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Reference
    ISearchService searchService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.selectList(null);
    }

    @Override
    public int innsertGoods(Goods goods) {
        System.out.println("添加的商品"+goods);
        int insert = goodsMapper.insert(goods);
/*        searchService.addGoods(goods);
        String s = HttpUtil.sendGet("http://127.0.0.1:8085/item/createhtml?gid=" + goods.getId());*/
        System.out.println(rabbitTemplate.toString());
        rabbitTemplate.convertAndSend("goods_exchange","",goods);
        return insert;
    }

    @Override
    public Goods getGoodsByid(Integer id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public int updateGoods(Goods goods) {
        return goodsMapper.updateById(goods);
    }

    @Override
    public int delete(Integer id) {
        return goodsMapper.deleteById(id);
    }

    @Override
    public List<Goods> getGoodsandType(Integer tid) {
        return null;
    }
}

