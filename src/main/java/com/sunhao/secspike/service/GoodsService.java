package com.sunhao.secspike.service;

import com.sunhao.secspike.vo.GoodsVO;

import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/20  21:21
 */
public interface GoodsService {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVO> getGoodsList();

    /**
     * 获得商品数量
     */
    int getStockCount(long goodsId);

    /**
     * 减少库存
     * @param goodsId 商品号
     * @return
     */
    Boolean reduceStockByVersion(long goodsId);

}
