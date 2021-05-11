package com.mooc.miaosha.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description: 用于接口防刷的注解
* @Param:
* @return: seconds过期时间，maxCount限制访问次数
* @Author: lishuaiyun
* @Date: 2021/5/8
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds();
    int maxCount();
    boolean needLogin() default true;
}
