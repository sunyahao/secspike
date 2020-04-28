package com.sunhao.secspike.rabbitmq;

import com.sunhao.secspike.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:23
 */
@Service
public class MQSender {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(String exchange, String routingKey, OrderMessage message){
        String msg = RedisService.beanToString(message);
        logger.info("send message:"+msg);
        rabbitTemplate.convertAndSend(exchange,routingKey,msg);
    }

    public void sendOrderMessageToDelayQueue(String exchange, String routingKey, OrderMessage message){
        String msg = RedisService.beanToString(message);
        logger.info("send message To delay Queue:"+msg);
        rabbitTemplate.convertAndSend(exchange,routingKey,msg);
    }
}
