package com.qf.Service;

import com.qf.entity.Goods;
import com.qf.entity.Page;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/11 19:25
 */
public interface ISearchService {
    Page<Goods> searchByKey(String keyword,Page page);
    int  addGoods(Goods goods);

}
