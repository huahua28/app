package com.ace.cms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;


/**
 * @Description: activity 数据源配置
 * @author: sanhu
 * @date: 2018年3月26日 下午4:58:25
 */
@Data
@Configuration
@EnableTransactionManagement
@ConfigurationProperties(prefix = "druid.datasource")
public class DruidConfig {
    public String url;
    public String username;
    public String password;
    public String driverClassName;
    public Integer initialSize;
    public Integer maxActive;
    public Integer minIdle;

    @Bean
    @Qualifier("druidDataSource")
    @Primary
    public DataSource dataSourceDruid(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(30000);
        dataSource.setTimeBetweenEvictionRunsMillis(10000);
        return dataSource;
    }

    @Bean
    public JdbcOperations activityJdbcOperations(@Qualifier("druidDataSource") DataSource druidDataSource) {
        return new JdbcTemplate(druidDataSource);
    }

    /**
     * @description: activity 库事务
     * @author sanhu
     * @date 2018/3/27 10:37
     * @param
     * @return
     */
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager accountTransactionManager(@Qualifier("druidDataSource") DataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }

}
