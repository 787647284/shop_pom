package com.qf.aop;

import java.lang.annotation.*;

/**
 * linzebin
 * 时间2019/7/21 18:54
 */
@Documented  //是否生成api文档
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {
    boolean mustLogin() default  false;
}
