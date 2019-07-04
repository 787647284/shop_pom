package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * linzebin
 * 时间2019/7/2 16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Power implements Serializable {
    @TableId(type = IdType.AUTO)
    private   Integer id;
    private   Integer pid;
    private   String powername;
    private   String powerpath;
    private   Date createtime=new Date();  /*实例化以后有默认值 才不会报错*/
    private   Integer status;
    @TableField(exist = false)
    private    String pname;
    @TableField(exist = false)
    private Boolean checked;

}
