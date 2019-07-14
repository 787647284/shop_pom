package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/5 15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
        /*主键自增，加了这个注解返回的就是插入的主键*/
        @TableId(type = IdType.AUTO)
        private Integer id;
        //商品名字
        private String gname;
        //商品详情
        private String ginfo;
        //商品图片
        private String gimage;
        /*价格，运算时准确，费性能*/
        private BigDecimal gprice;
        //商品类型
        private Integer tid;
        //商品库存
        private Integer gsave;

        ////级联 查询商品的同时获该商品类型
        @TableField(exist = false)
        private List<GoodsType> goodsTypes;
}
