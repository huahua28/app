package com.ace.cms.annotation;

import java.lang.annotation.*;

/**
 * @author sanhu
 * @description: 自定义注解 拦截service
 * @date 2018/4/26 14:49
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceAspect {
}