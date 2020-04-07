package com.sunhao.secspike.service;

import com.sunhao.secspike.bean.OrderInfo;
import com.sunhao.secspike.vo.SprikeGoodsVO;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  12:22
 */
public interface SpikeGoodsService {

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    SprikeGoodsVO getGoodsDetail(long goodsId);

    OrderInfo secSpike(long userId, long goodsId);

    String getSpikeResult(long userId, long goodsId);

    public String createPath(long goodsId);

    public Boolean checkPath(long goodsId, String path);
}
