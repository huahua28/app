package com.ace.cms.service;

import com.ace.cms.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolTest extends BaseServiceTest{

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void test1() {
        Jedis jedis = jedisPool.getResource();
        jedis.set("test","1");
        System.out.println(jedis.get("test"));

    }

}
