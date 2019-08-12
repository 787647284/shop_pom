package com.qf.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * linzebin
 * 时间2019/7/26 20:46
 */
@Component
@ConfigurationProperties(prefix = "spring.orderdb2.datasource")
public class Db2DataSource extends BaseDataSource {

}
