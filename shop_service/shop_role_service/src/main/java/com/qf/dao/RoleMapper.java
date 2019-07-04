package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Role;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/1 21:50
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> queryListByUid(Integer id);
}
