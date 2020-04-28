package com.sunhao.secspike.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/4/26  16:48
 */
@Configuration
@AllArgsConstructor
public class MQInitializingBean implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(new MQConfirmCallback());
        rabbitTemplate.setReturnCallback(new MQReturnCallBack());
    }
}
