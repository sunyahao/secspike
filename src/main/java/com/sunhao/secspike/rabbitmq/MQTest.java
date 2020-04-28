package com.sunhao.secspike.rabbitmq;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/4/5  12:37
 */
public class MQTest {
    public static void main(String[] args) {
        long goodsId = 1;
        long username = 1111;
        OrderMessage message = new OrderMessage();
        message.setGoodsId(goodsId);
        message.setUserId(username);
        MQSender sender = new MQSender();
        sender.sendOrderMessage(MQConfig.LOG_USER_EXCHANGE_NAME,MQConfig.LOG_USER_BINDING_KEY,message);
    }
}
