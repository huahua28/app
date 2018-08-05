package com.ace.cms.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by  on 2017/6/23.
 * Copyright (c) 2017
 * Desc: (service方法请求参数及执行时间记录). <br/>
 */

@Aspect
@Order(value=0)
@Component
@Slf4j
public class ServiceLogAspect {

    static ThreadLocal<HashMap> threadmap = new ThreadLocal<HashMap>() {
        @Override
        protected HashMap initialValue() {
            return new HashMap();
        }
    };

    static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Pointcut("execution(* com.ace.cms.service..*.*(..))")
    public void serviceLog() {

    }

    @Before("serviceLog()")
    public void doBefore(JoinPoint joinPoint){
        //记录开始方法执行开始时间
        threadmap.get().put("startTime", new Date());
    }

    @AfterReturning(pointcut = "serviceLog()")
    public void doAfter(JoinPoint joinPoint){
        Date endTime = new Date();
        Date startTime = (Date) threadmap.get().get("startTime");
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        StringBuilder info = new StringBuilder();
        info
                .append("Method: ")
                .append(method)
                .append(",Args: ")
                .append(Arrays.toString(joinPoint.getArgs()))
                .append(",StartAt: ").append(sd.format(startTime))
                .append(",EndAt: ")
                .append(sd.format(endTime))
                .append(",耗时: ")
                .append(endTime.getTime()-startTime.getTime())
                .append("毫秒");

        log.info(info.toString());
    }
}
