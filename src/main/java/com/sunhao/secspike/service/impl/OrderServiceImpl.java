package com.sunhao.secspike.service.impl;

import com.sunhao.secspike.bean.Order;
import com.sunhao.secspike.mapper.OrderMapper;
import com.sunhao.secspike.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:16
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order getOrder(long userId, long goodsId) {
        return orderMapper.getOrder(userId,goodsId);
    }

}
