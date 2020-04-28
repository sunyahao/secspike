package com.sunhao.secspike.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/4/26  16:45
 */
public class MQReturnCallBack implements RabbitTemplate.ReturnCallback {

    private static Logger logger = LoggerFactory.getLogger(MQReturnCallBack.class);
    /**
     * 消息发送至RabbitMQ的exchange没有匹配的队列
     * @param message
     * @param i
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info("消息主题 messgae："+message);
        logger.info("消息主题 message："+i);
        logger.info("描述："+s);
        logger.info("消息使用的交换器："+s1);
        logger.info("消息使用的路由键："+s2);
    }
}
