package com.mooc.miaosha.redis;

public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getMiaoshaOrderByuidGid = new OrderKey("moug");
}
