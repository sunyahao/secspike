package com.sunhao.secspike.service;

import com.sunhao.secspike.bean.OrderInfo;

import java.util.List;

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
    OrderInfo getOrder(long userId, long goodsId);

    OrderInfo createOrder(long userId, long goodsId);

    List<OrderInfo> getAllOrder(long userId);
}
