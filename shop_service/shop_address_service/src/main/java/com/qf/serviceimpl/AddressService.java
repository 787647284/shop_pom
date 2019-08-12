package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.Service.IAddressService;
import com.qf.dao.AddressMapper;
import com.qf.entity.Address;
import com.qf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/23 20:13
 */
@Service
public class AddressService implements IAddressService {

        @Autowired
        AddressMapper addressMapper;
        public List<Address> getAddressList(User user){
               QueryWrapper queryWrapper=new QueryWrapper();
               queryWrapper.eq("uid",user.getId());
              return addressMapper.selectList(queryWrapper);
        }

    @Override
    public int insertAddress(User user, Address address) {
        address.setUid(user.getId());
        return addressMapper.insertAddress(address);
    }
    public Address getAddressByid(Integer aid){
            return addressMapper.selectById(aid);
    }
}
