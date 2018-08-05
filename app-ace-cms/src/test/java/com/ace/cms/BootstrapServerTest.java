package com.ace.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

@SpringBootApplication
@EnableCaching
public class BootstrapServerTest {
    public static void main(String[] args) {
        SpringApplication.run(BootstrapServerTest.class, args);
    }
}