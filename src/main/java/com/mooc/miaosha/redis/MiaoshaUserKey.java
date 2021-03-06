package com.mooc.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private MiaoshaUserKey(String prefix){
        super(prefix);
    }

    private MiaoshaUserKey(int expireSeconds,String prefix){
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");
//    public static MiaoshaUserKey getByName = new MiaoshaUserKey("name");
}
