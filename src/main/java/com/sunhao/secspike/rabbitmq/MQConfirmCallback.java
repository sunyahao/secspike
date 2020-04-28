package com.sunhao.secspike.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/4/26  16:46
 */
public class MQConfirmCallback implements RabbitTemplate.ConfirmCallback {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    /**
     * 生产者发送消息后RabbitMQ Broker confirm后触发的回调函数
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info("消息唯一ID："+correlationData);
        logger.info("确认结果："+b);
        logger.info("原因："+s);
    }
}
