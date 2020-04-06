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
public class SpikeGoods {
    private Long id;
    private Long goodsId;
    private String goodsName;
    private String goodsImg;
    private Integer stockCount;
    private Double spikePrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date endTime;
    private int version;
    private Boolean isactive;
    private Boolean canKill;

}
