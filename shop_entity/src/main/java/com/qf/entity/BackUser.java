package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/1 19:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
            //表明默认会去掉下划线，然后首字母变成大写
public class BackUser implements Serializable {
        @TableId(type = IdType.AUTO)
        private Integer id;
        private String username;
        private String password;
        private String name;
        private Integer sex;
        private Date createtime=new Date();
        /**
         * 状态值
         */
        private Integer status;
        //查询当前用户所有的角色以及权限
        @TableField(exist = false)
        private List<Power> powers;
        @TableField(exist = false)
        private List<Role> roles;

}
