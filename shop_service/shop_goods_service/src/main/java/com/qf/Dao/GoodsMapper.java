package com.qf.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Goods;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/5 15:25
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> getGoodsandType(Integer tid);
}
