package com.ace.cms.config;

import lombok.Data;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @description: mybatis 配置
 * @author sanhu
 * @date 2018/3/27 10:38
 * @param
 * @return
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mybatis")
@MapperScan(basePackages = "com.ace.cms.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {

    private String mapperLocations;


    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("druidDataSource") DataSource druidDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(druidDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        factoryBean.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis-config.xml"));
        return factoryBean.getObject();
    }
    
}
