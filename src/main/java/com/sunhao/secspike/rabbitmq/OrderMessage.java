package com.sunhao.secspike.rabbitmq;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:23
 */
@Getter
@Setter
public class OrderMessage {
    private long userId;
    private long goodsId;
}
