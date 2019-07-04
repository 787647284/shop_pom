package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.BackUser;

/**
 * linzebin
 * 时间2019/7/1 20:05
 */
public interface BackMapper extends BaseMapper<BackUser> {
    BackUser queryByUserName(String username);
}
