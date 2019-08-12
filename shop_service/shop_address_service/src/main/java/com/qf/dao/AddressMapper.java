package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Address;
import com.qf.entity.User;

/**
 * linzebin
 * 时间2019/7/23 20:14
 */
public interface AddressMapper extends BaseMapper<Address> {
      public int insertAddress(Address address);
}
