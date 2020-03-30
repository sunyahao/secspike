package com.sunhao.secspike.redis;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/28  13:27
 */
public interface KeyPrefix {

    /**
     * 设置redis有效期
     * @return
     */
    int expireSeconds();

    /**
     * 获得key的前缀
     * @return
     */
    String getPrefix();
}
