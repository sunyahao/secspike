package com.sunhao.secspike.bean;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunhao on 2020/3/20.
 */
@Getter
@Setter
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsImg;
    private String code;
    private Double goodsPrice;
    private Integer goodsStock;
}
