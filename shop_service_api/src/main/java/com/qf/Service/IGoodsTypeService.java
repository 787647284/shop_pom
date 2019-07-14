package com.qf.Service;

import com.qf.entity.GoodsType;

import java.util.List;

public interface IGoodsTypeService {
    List<GoodsType> getGoodsType();
    int insertGoodsType(GoodsType goodsType);
    List<GoodsType> getGoodsTypeByid(Integer id);
}
