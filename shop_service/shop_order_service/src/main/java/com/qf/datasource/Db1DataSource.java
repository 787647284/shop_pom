package com.qf.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * linzebin
 * 时间2019/7/26 20:46
 */
//读取前缀  自动将配置文件映射到
@Component
@ConfigurationProperties(prefix = "spring.orderdb1.datasource")
public class Db1DataSource extends BaseDataSource{

}
