package com.sunhao.secspike.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  11:12
 */
@Getter
@Setter
public class GoodsVO {
    private Long goodsId;
    private String goodsName;
    private String goodsImg;
    private Integer stockCount;
    private Boolean canKill;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date endTime;
}
