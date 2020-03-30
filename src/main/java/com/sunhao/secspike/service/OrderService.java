package com.sunhao.secspike.service;

import com.sunhao.secspike.bean.Order;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:15
 */
public interface OrderService {

    /**
     * 从ss_order表中获得信息
     * @param userId
     * @param goodsId
     * @return
     */
    Order getOrder(long userId, long goodsId);
}
