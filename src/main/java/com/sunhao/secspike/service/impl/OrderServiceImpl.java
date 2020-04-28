package com.sunhao.secspike.service.impl;

import com.sunhao.secspike.bean.Order;
import com.sunhao.secspike.bean.OrderInfo;
import com.sunhao.secspike.bean.SpikeGoods;
import com.sunhao.secspike.mapper.GoodsMapper;
import com.sunhao.secspike.mapper.OrderMapper;
import com.sunhao.secspike.rabbitmq.MQConfig;
import com.sunhao.secspike.rabbitmq.MQSender;
import com.sunhao.secspike.rabbitmq.OrderMessage;
import com.sunhao.secspike.service.OrderService;
import com.sunhao.secspike.util.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:16
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1);
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MQSender sender;

    @Override
    public OrderInfo getOrder(long userId, long goodsId) {
        return orderMapper.getOrder(userId,goodsId);
    }

    /**
     * 创建订单，分别在ss_order和ss_order_info中添加订单记录
     * 使用雪花算法生成订单号
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    @Transactional
    public OrderInfo createOrder(long userId, long goodsId){
        SpikeGoods goods = goodsMapper.getGoodsByGoodsId(goodsId);
        OrderInfo orderInfo = new OrderInfo();
        try{
            orderInfo.setGoodsId(goodsId);
            orderInfo.setUserId(userId);
            orderInfo.setStatus(0);
            orderInfo.setGoodsName(goods.getGoodsName());
            orderInfo.setPrice(goods.getSpikePrice());
            orderInfo.setCreateTime(new Date());
            //使用雪花算法生成订单号
            String orderId = String.valueOf(idWorker.nextId());
            orderInfo.setOrderId(orderId);
            orderMapper.insertOrderInfo(orderInfo);

            Order order = new Order();
            order.setOrderId(orderId);
            order.setGoodsId(goodsId);
            order.setUserId(userId);
            orderMapper.insertOrder(order);

            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setOrderId(orderId);
            sender.sendOrderMessageToDelayQueue(MQConfig.DELAY_EXCHANGE_NAME, "delay.order.routing.key", orderMessage);
        } catch(Exception e){
            log.error("创建订单错误");
            e.printStackTrace();
        }
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getAllOrder(long userId) {
        return orderMapper.getAllOrder(userId);
    }

    @Override
    public int getOrderStatus(String orderId) {
        int status = orderMapper.getOrderStatus(orderId);
        return status;
    }

    @Override
    public Boolean cancelOrder(String orderId) {
        int res = orderMapper.updateOrderStatusToTwo(orderId);
        return res > 0;
    }

    @Override
    public OrderInfo getOrderDetail(String orderId) {
        OrderInfo orderInfo = orderMapper.getOrderDetail(orderId);
        return orderInfo;
    }

    @Override
    public String getOrderId(long userId, long goodsId) {
        String orderId = orderMapper.getOrderId(userId,goodsId);
        return orderId;
    }

    @Override
    public Boolean payForOrder(String orderId) {
        int res = orderMapper.updateOrderStatusToOne(orderId);
        return res>0;
    }
}
