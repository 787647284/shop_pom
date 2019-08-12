package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IAddressService;
import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * linzebin
 * 时间2019/7/25 13:22
 */
@Controller
@RequestMapping("/address")
public class AddressController {
    @Reference
    IAddressService addressService;
    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    public String insert(Address address, User user){
          addressService.insertAddress(user, address);
          return "redirect:/order/edit";
    }

}
