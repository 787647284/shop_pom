package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/26 19:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Order implements Serializable {
    //非自增长 手动输入
    @TableId(type = IdType.INPUT)
    private String orderid;
    private String person;
    private String address;
    private String phone;
    //商品总价
    private BigDecimal allprice;
    private Date  createtime;
    private Integer status;//0等于未支付 1-等于已支付代发货-2等于已发货 3收货
    private Integer uid;

    private List<OrderDetils> orderDetils;
}
