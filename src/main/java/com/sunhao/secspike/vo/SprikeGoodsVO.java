package com.sunhao.secspike.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  11:11
 */
@Getter
@Setter
public class SprikeGoodsVO {
    private  Long goodsId;
    private String goodsName;
    private Integer stockCount;
    private Double spikePrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date endTime;
    private Boolean canKill;
}
