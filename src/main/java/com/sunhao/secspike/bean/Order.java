package com.sunhao.secspike.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by sunhao on 2020/3/20.
 */
@Getter
@Setter
public class Order {
    private String orderId;
    private Long userId;
    private Long goodsId;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date createTime;
}
