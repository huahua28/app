package com.ace.cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chenxiangqi
 * @date 2017/12/18 上午11:37
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {


    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int timeout;
    private int maxAttempts;
    private String redisAddress;
    private String password;
    private int minEvictableIdleTimeMillis;
    private int numTestsPerEvictionRun;
    private int timeBetweenEvictionRunsMillis;

    @Bean
    public JedisPoolConfig initJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        return jedisPoolConfig;
    }


    @Bean
    public JedisPool initJedisPool(JedisPoolConfig jedisPoolConfig) {
        String[] split = redisAddress.split(",");
        String[] node = split[0].split(":");
        String host = node[0];
        int port = Integer.valueOf(node[1]);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host, port, timeout, password);
        return jedisPool;
    }
}
