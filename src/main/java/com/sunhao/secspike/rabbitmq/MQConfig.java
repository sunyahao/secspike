package com.sunhao.secspike.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:22
 */
@Configuration
public class MQConfig {

    private static final Logger log = LoggerFactory.getLogger(MQConfig.class);
    //    队列名
    public static final String LOG_USER_QUEUE_NAME = "log.user.queue";
    //    交换机名
    public static final String LOG_USER_EXCHANGE_NAME = "log.user.exchange";
    //    路由键名
    public static final String LOG_USER_ROUTING_KEY = "log.user.routing.key";


    @Bean
    public Queue logUserQueue(){
        return new Queue(LOG_USER_QUEUE_NAME,true);
    }

    @Bean
    public DirectExchange logUserExchange(){
        return new DirectExchange(LOG_USER_EXCHANGE_NAME,true,false);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with(LOG_USER_ROUTING_KEY);
    }
}
