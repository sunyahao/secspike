package com.sunhao.secspike.rabbitmq;

import com.sunhao.secspike.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:23
 */
@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendOrderMessage(String exchange, String routingKey, OrderMessage message){
        String msg = RedisService.beanToString(message);
        log.info("send message:"+msg);
        amqpTemplate.convertAndSend(exchange,routingKey,msg);
        System.out.println(2);
    }
}
