package com.sunhao.secspike.rabbitmq;

import com.sunhao.secspike.bean.OrderInfo;
import com.sunhao.secspike.redis.RedisService;
import com.sunhao.secspike.service.GoodsService;
import com.sunhao.secspike.service.OrderService;
import com.sunhao.secspike.service.SpikeGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;



/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:24
 */
@Service
public class MQReceiver {

    private final static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SpikeGoodsService spikeGoodsService;

    @RabbitListener(queues = MQConfig.LOG_USER_QUEUE_NAME)
    public void receive(String message) throws IOException {
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
        OrderInfo orderInfo = orderService.getOrder(userId,goodsId);
        if(orderInfo != null){
            log.info("用户"+userId+"已经购买过商品号为"+goodsId+"的商品");
            return ;
        }
        spikeGoodsService.secSpike(userId, goodsId);
//        channel.basicAck(message1.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = MQConfig.DELAY_ORDER_PROCESS_QUEUE_NAME)
    public void dealDelayQueue(String message) throws IOException{
        System.out.println("test 1");
        OrderMessage omsg = RedisService.stringToBean(message,OrderMessage.class);
        String orderId = omsg.getOrderId();
        int status = orderService.getOrderStatus(orderId);
        if(status == 0){
            orderService.cancelOrder(orderId);
        }
//        channel.basicAck(message1.getMessageProperties().getDeliveryTag(), false);
    }
}
