package com.qf.Service;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {
    List<Goods> getGoodsList();
    int innsertGoods(Goods goods);
    Goods getGoodsByid(Integer id);
    int updateGoods(Goods goods);
    int delete(Integer id);
    List<Goods> getGoodsandType(Integer tid);
}
