package com.ace.cms.filter;

import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.utils.LogUtils;
import com.ace.cms.vo.Response;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by  on 2017/6/23.
 * Copyright (c) 2017, 
 * Desc: (打印请求参数). <br/>
 */
@Slf4j
public class RequestParamLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        StringBuilder requestParams = new StringBuilder();
        requestParams
                .append(request.getRequestURL().toString())
                .append("?")
                .append(LogUtils.buildBodyLog(request));

        log.info(requestParams.toString());

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.error("Error RequestParamLogFilter.doFilter :{};e:{}",e.getMessage(),e);
            throw new WebControllerException(ErrorCode.SYS_ERR);
        }


    }
    
    @Override
    public void destroy() {

    }
}
