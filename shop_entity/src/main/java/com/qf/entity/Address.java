package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * linzebin
 * 时间2019/7/23 19:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //地址所属用户
    private Integer uid;
    //收货人
    private String person;
    //收货地址
    private String address;
    //收货人电话
    private String phone;
    //是否默认地址
    private Integer isdefault=0;
    //创建时间
    private Date createtime=new Date();

}
