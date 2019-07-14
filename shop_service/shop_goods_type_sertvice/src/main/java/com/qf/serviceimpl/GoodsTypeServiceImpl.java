package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.Service.IGoodsService;
import com.qf.Service.IGoodsTypeService;
import com.qf.dao.GoodsTypeMapper;
import com.qf.entity.GoodsType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/6 11:38
 */
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService {
    @Autowired
    GoodsTypeMapper goodsTypeMapper;
    @Override
    public List<GoodsType> getGoodsType() {
        return goodsTypeMapper.getGoodsType();
    }

    @Override
    public int insertGoodsType(GoodsType goodsType) {
        return goodsTypeMapper.insert(goodsType);
    }

    @Override
    public List<GoodsType> getGoodsTypeByid(Integer id) {
        return goodsTypeMapper.getGoodsTypeByid(id);
    }
}
