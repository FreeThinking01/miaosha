package com.mooc.miaosha.redis;

public interface KeyPrefix {
    /**
    * @Description: 接口中的变量默认都是public,不必再使用public修饰
    */
    int expireSeconds();

    String getPrefix();
}
