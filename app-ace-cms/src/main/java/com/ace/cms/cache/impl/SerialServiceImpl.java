package com.ace.cms.cache.impl;

import com.ace.cms.cache.SerialService;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;


@Slf4j
@Service("serialService")
public class SerialServiceImpl implements SerialService {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public Long get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            Long seq = jedis.incr(key);
            if (Objects.equals(seq, null)) {
                seq = jedis.incr(key);
            }

            if (Objects.equals(seq, null)) {
                throw new WebControllerException(ErrorCode.SYS_ERR);
            }
            return seq;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}



