package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * linzebin
 * 时间2019/7/18 20:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email implements Serializable {
        //发送给谁
    private String to;
    //  标题
    private String subject;
    //内容
    private String content;
}
