package com.ace.cms.service;

import com.ace.cms.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChannelServiceTest extends BaseServiceTest {

    @Autowired
    private ChannelService channelService;


    @Test
    public void test() {

        channelService.add(28, 19l);
    }



}
