package com.qf.dataconfig;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * linzebin
 * 时间2019/7/26 20:50
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    //动态获取数据源
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("动态数据源得关键字"+threadLocal.get());
        return threadLocal.get();
    }
    //数据源选择
    private static ThreadLocal<String> threadLocal=new ThreadLocal<>();
    public static void set(String datasourceKeywork){
            threadLocal.set(datasourceKeywork);
    };
}
