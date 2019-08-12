package com.qf.Service;

import com.qf.entity.Address;
import com.qf.entity.User;

import java.util.List;

public interface IAddressService {

    public List<Address> getAddressList(User user);
    public int insertAddress(User user,Address address);
    public Address getAddressByid(Integer aid);
}
