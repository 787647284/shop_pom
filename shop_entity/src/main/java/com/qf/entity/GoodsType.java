package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/6 11:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "goodstype")
public class GoodsType implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String typename;
    private Integer status;
    private Date createtime=new Date();
    @TableField(exist = false)
    private String pname;
    @TableField(exist = false)
    private Boolean checked;
    @TableField(exist = false)
    private List<Goods> goods;
}
