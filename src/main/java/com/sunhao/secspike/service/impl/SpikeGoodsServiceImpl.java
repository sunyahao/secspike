package com.sunhao.secspike.service.impl;

import com.sunhao.secspike.bean.OrderInfo;
import com.sunhao.secspike.mapper.SpikeGoodsMapper;
import com.sunhao.secspike.redis.GoodsKey;
import com.sunhao.secspike.redis.RedisService;
import com.sunhao.secspike.service.GoodsService;
import com.sunhao.secspike.service.OrderService;
import com.sunhao.secspike.service.SpikeGoodsService;
import com.sunhao.secspike.util.MD5Util;
import com.sunhao.secspike.util.SnowflakeIdWorker;
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

    @Autowired
    private RedisService redisService;

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

    /**
     * 进行减库存，下订单，创建订单操作
     * @return
     */
    @Transactional
    public OrderInfo secSpike(long userId, long goodsId){
        //减库存
        boolean isReduce = goodsService.reduceStockByVersion(goodsId);
        if(isReduce){
            OrderInfo orderInfo = orderService.createOrder(userId,goodsId);
            return orderInfo;
        }
        return null;
    }

    /**
     * 获取秒杀结果，秒杀成功返回订单号，排队中返回0，秒杀失败返回-1
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public String getSpikeResult(long userId, long goodsId) {
        OrderInfo order = orderService.getOrder(userId, goodsId);
        if(order != null){
            return order.getOrderId();
        }
        int stockCount = goodsService.getStockCount(goodsId);
        if(stockCount > 0){
            return "0";
        }
        return "-1";
    }

    /**
     * 生成商品的动态地址
     * @param goodsId
     * @return
     */
    public String createPath(long goodsId){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(2);
        String path = MD5Util.md5(String.valueOf(idWorker.nextId()));
        redisService.set(GoodsKey.getGoodsUrl,""+goodsId,path);
        return path;
    }

    /**
     * 判断url地址是否与缓存中的相同
     * @param goodsId
     * @param path
     * @return
     */
    public Boolean checkPath(long goodsId, String path){
        if(path == null){
            return false;
        }
        String path1 = redisService.get(GoodsKey.getGoodsUrl,""+goodsId,String.class);
        if(path1.equals(path)){
            return true;
        }
        return false;
    }
}
