package com.sunhao.secspike.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
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
    //    绑定键名
    public static final String LOG_USER_BINDING_KEY = "log.user.routing.key";

    //    将消息发送到延迟队列的交换机
    public static final String DELAY_EXCHANGE_NAME = "delay.exchange";

    //     延迟队列
    public static final String DELAY_QUEUE_PER_QUEUE_NAME = "delay.queue.per.queue";

    //     普通交换机的延迟队列的绑定键
    public static final String DELAY_EXCHANGE_BINDING_KEY = "delay.*.routing.key";

    //      DEAD LETTER EXCHANGE
    public static final String DELAY_ORDER_PROCESS_EXCHANGE_NAME = "delay.order.process.exchange";

    //      实际消费队列
    public static final String DELAY_ORDER_PROCESS_QUEUE_NAME = "delay.order.process.queue";

    public static final long ttl = 1800000;//30min

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
        return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with(LOG_USER_BINDING_KEY);
    }


//    /**
//     *json序列化时强制手动ack
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//json序列化时，若想手动ACK，则必须配置
//        return factory;
//    }

    @Bean
    public Queue delayQueuePerQueue(){
        return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange",DELAY_ORDER_PROCESS_EXCHANGE_NAME)
                .withArgument("x-message-ttl",ttl)
                .build();
    }

    @Bean
    public TopicExchange delayExchange(){
        return ExchangeBuilder.topicExchange(DELAY_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public Binding delayBinding(){
        return BindingBuilder.bind(delayQueuePerQueue()).to(delayExchange()).with(DELAY_EXCHANGE_BINDING_KEY);
    }

    @Bean
    public Queue delayOrderProcessQueue(){
        return QueueBuilder.durable(DELAY_ORDER_PROCESS_QUEUE_NAME)
                .build();
    }

    @Bean
    public FanoutExchange delayOrderProcessExchange(){
        return ExchangeBuilder.fanoutExchange(DELAY_ORDER_PROCESS_EXCHANGE_NAME)
                .build();
    }

    @Bean
    public Binding processBinding(){
        return BindingBuilder.bind(delayOrderProcessQueue()).to(delayOrderProcessExchange());
    }
}
