package com.ace.cms.service;

import com.ace.cms.BaseServiceTest;
import com.ace.cms.cache.SerialService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void test() {

        boolean register = userService.register("1595604547","123456","中国户",1,"1234","url");
        System.out.println(register);
    }

    @Test
    public void test1() {
        userService.realname(1l,"test","321002211223212342","teset","test");
    }


}
