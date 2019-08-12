package com.qf.dataconfig;

import com.qf.datasource.Db1DataSource;
import com.qf.datasource.Db2DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * linzebin
 * 时间2019/7/26 20:55
 */
@Configuration
public class MybatisConfig {
    @Autowired
    private Db1DataSource db1DataSource;
    @Autowired
    private Db2DataSource db2DataSource;
    public void init(){
        System.out.println("数据源1："+db1DataSource);
        System.out.println("数据源2："+db2DataSource);
    }
    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocation;

    /**
     * 配置动态数据源
     */
    @Bean
    public DynamicDataSource getDataSource(){
        Map<Object,Object> map=new HashMap<>();
        map.put(db1DataSource.getKeyword(),db1DataSource.getDataSource());
        map.put(db2DataSource.getKeyword(),db2DataSource.getDataSource());
        System.out.println("动态数据源"+map);
        DynamicDataSource dynamicDataSource=new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(db1DataSource.getDataSource());
        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }
    @Bean
public SqlSessionFactoryBean getSqlSessionFactoryBean(DynamicDataSource getDataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource);
    try {
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
    } catch (IOException e) {
        e.printStackTrace();
    }
    return sqlSessionFactoryBean;
}

}
