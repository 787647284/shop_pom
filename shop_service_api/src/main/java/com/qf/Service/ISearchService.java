package com.qf.Service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/11 19:25
 */
public interface ISearchService {
    List<Goods> searchByKey(String keyword);
    int  addGoods(Goods goods);

}
