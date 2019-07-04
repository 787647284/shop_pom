package com.qf.shop_role_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
@MapperScan("com.qf.dao")
@DubboComponentScan("com.qf.ServiceImpl")
public class ShopRoleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopRoleServiceApplication.class, args);
    }

}
