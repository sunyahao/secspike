package com.sunhao.secspike.service.impl;

import com.sunhao.secspike.mapper.SpikeGoodsMapper;
import com.sunhao.secspike.service.GoodsService;
import com.sunhao.secspike.service.OrderService;
import com.sunhao.secspike.service.SpikeGoodsService;
import com.sunhao.secspike.vo.SprikeGoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  12:23
 */
@Service
public class SpikeGoodsServiceImpl implements SpikeGoodsService {

    private final static Logger log = LoggerFactory.getLogger(SpikeGoodsServiceImpl.class);

    @Autowired
    private SpikeGoodsMapper spikeGoodsMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 获得商品的详细信息
     * @param goodsId
     * @return
     */
    @Override
    public SprikeGoodsVO getGoodsDetail(long goodsId) {
        if(goodsId <= 0){
            log.error("商品编号有误");
            return null;
        }
        return spikeGoodsMapper.getGoodsDetail(goodsId);
    }

    @Transactional
    public
}
