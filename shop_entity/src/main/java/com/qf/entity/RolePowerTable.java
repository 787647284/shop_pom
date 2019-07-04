package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * linzebin
 * 时间2019/7/3 15:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePowerTable implements Serializable {
        private Integer rid;
        private Integer pid;
}
