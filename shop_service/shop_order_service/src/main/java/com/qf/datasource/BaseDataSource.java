package com.qf.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

import javax.sql.DataSource;

/**
 * linzebin
 * 时间2019/7/26 20:43
 */
@Data
public class BaseDataSource {

    protected String url;
    protected String username;
    protected String password;
    protected String driverClassName;
    protected String keyword;

    public DataSource getDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driverClassName);
        return hikariDataSource;
    }
}
