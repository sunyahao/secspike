package com.sunhao.secspike.redis;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/28  13:43
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix){
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getGoodsStock = new GoodsKey(0, "gs");
    public static GoodsKey getGoodsUrl = new GoodsKey(0,"gu");
}
