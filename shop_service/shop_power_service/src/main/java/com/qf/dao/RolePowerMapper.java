package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Power;
import com.qf.entity.RolePowerTable;

import java.util.List;

public interface RolePowerMapper extends BaseMapper<RolePowerTable> {
    List<Power> rolePower(Integer id);
}
