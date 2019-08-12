package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * linzebin
 * 时间2019/7/26 19:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetils implements Serializable {
        @TableId(type = IdType.AUTO)
        private Integer id;
        private String orderid;
        private Integer gid;
        private String gname;
        //单价
        private BigDecimal gprice;
        private String gimage;
        private Integer gnumber;
        //订单总价
        private BigDecimal sprice;

}
