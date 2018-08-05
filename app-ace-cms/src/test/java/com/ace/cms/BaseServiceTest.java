package com.ace.cms;

import lombok.extern.slf4j.Slf4j;
import mockit.MockUp;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootstrapServerTest.class)
/*@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class
        TransactionalTestExecutionListener.class
})*/
//@WebAppConfiguration
//@Transactional(rollbackFor = Exception.class)
@Slf4j
public abstract class BaseServiceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    // mock new Date
    protected void mockTime(final Date date) {
        new MockUp<System>() {
            @mockit.Mock
            public long currentTimeMillis() {
                Date fake = date;
                return fake.getTime();
            }
        };
    }

    // mock random
    protected void mockRandom(final String number) {
        new MockUp<RandomStringUtils>() {
            @mockit.Mock
            public String randomNumeric(int count) {
                return number;
            }
        };
    }


}