package com.sunhao.secspike.redis;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:04
 */
public class OrderKey extends BasePrefix{

    private OrderKey(String prefix){
        super(prefix);
    }

    public final static OrderKey getSpikeOrderByUserAndGoods = new OrderKey("so");
}
