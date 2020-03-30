package com.sunhao.secspike.rabbitmq;

import com.sunhao.secspike.bean.Order;
import com.sunhao.secspike.redis.RedisService;
import com.sunhao.secspike.service.GoodsService;
import com.sunhao.secspike.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:24
 */
public class MQReceiver {

    private final static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        OrderMessage msg = RedisService.stringToBean(message,OrderMessage.class);
        long userId = msg.getUserId();
        long goodsId = msg.getGoodsId();

        //因为消息队列有延迟性，所以每轮到一条消息都要判断该商品是否还有库存,避免超卖
        int stockCount = goodsService.getStockCount(goodsId);
        if(stockCount <= 0){
            log.info("用户"+userId+",商品号为"+goodsId+"的商品已售罄");
            return ;
        }

//        因为消息队列有延迟性，所以可能存在用户多次购买的现象，比如消息队列中有一条未被处理到的a的购买记录，而客户端的a再次购买了该商品，那么就会
//        进入到该队列中来
        Order order = orderService.getOrder(userId,goodsId);
        if(order != null){
            log.info("用户"+userId+"已经购买过商品号为"+goodsId+"的商品");
            return ;
        }


    }
}
