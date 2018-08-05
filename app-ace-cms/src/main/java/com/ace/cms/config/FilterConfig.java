package com.ace.cms.config;

import com.ace.cms.filter.CheckFilter;
import com.ace.cms.filter.RequestParamLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author sanhu
 * @description: filter config
 * @date 2018/4/11 12:49
 */
@Configuration
@Slf4j
public class FilterConfig {

    /**
     * 需要做防重复点击的请求集合
     */
    private Set<String> repeatClickUrl = new HashSet<>();

    @PostConstruct
    public void init() throws ServletException {
        Properties urlsProperties = new Properties();
        InputStreamReader isr = null;
        try {
            InputStream in = this.getClass().getResourceAsStream("/repeat-click-url.yml");
            isr = new InputStreamReader(in, StandardCharsets.UTF_8);
            urlsProperties.load(isr);
            Enumeration<?> enumeration = urlsProperties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String name = (String) enumeration.nextElement();
                if (name.startsWith("/")) {
                    repeatClickUrl.add(name);
                }
            }
        } catch (IOException e) {
            log.error("initialize FilterConfig error: ", e);
            throw new ServletException(e);
        } finally {
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    log.error("initialize FilterConfig error: ", e);
                }
            }
        }
    }

    /**
     * @description: 请求参数校验filter
     * @author sanhu
     * @date 2018/5/17 10:58
     */
    @Bean
    public FilterRegistrationBean requestParamLogFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestParamLogFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestParamLogFilter");
        registration.setOrder(0);
        return registration;
    }

}
