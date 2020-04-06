package com.sunhao.secspike.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/30  15:03
 */
@Getter
@Setter
public class Order {
    private String orderId;
    private Long userId;
    private Long goodsId;
}
