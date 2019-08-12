package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;

/**
 * linzebin
 * 时间2019/7/22 20:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCart implements Serializable {
    @TableId(type = IdType.AUTO)
   private Integer id;
   private Integer gid;
   private Integer uid;
   private Integer gnumber;
   private BigDecimal sprice;
   private Date createtime;
   //一个购物车对象存入一个商品
    @TableField(exist = false)
    private Goods goods;
}
