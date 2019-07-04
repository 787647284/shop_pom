package com.qf.shop_power_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.qf")
@MapperScan("com.qf.dao")
@DubboComponentScan("com.qf.service")
@EnableTransactionManagement
public class ShopPowerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopPowerServiceApplication.class, args);
    }

}
