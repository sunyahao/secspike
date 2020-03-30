package com.sunhao.secspike.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:22
 */
public class MQConfig {

    public static final String QUEUE = "queue";


    /**
     * Direct模式 交换机Exchange
     * 发送者先发送到交换机上，然后交换机作为路由再将信息发到队列，
     * */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }
}
