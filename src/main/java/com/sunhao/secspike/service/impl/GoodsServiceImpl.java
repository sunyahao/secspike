package com.sunhao.secspike.service.impl;

import com.sunhao.secspike.bean.SpikeGoods;
import com.sunhao.secspike.mapper.GoodsMapper;
import com.sunhao.secspike.service.GoodsService;
import com.sunhao.secspike.vo.GoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/27  21:03
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private final static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    //乐观锁最大尝试次数
    private final static int MAX_TRIES = 10;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVO> getGoodsList() {
        return goodsMapper.getGoodsList();
    }

    @Override
    public int getStockCount(long goodsId) {
        return goodsMapper.getStockCount(goodsId);
    }

    /**
     * 减库存成功返回true
     * @param goodsId 商品号
     * @return
     */
    @Override
    public Boolean reduceStockByVersion(long goodsId) {
        int attempt = 0;
        int res = 0;
        SpikeGoods sg = new SpikeGoods();
        sg.setGoodsId(goodsId);
        do{
            attempt++;
            try{
                sg.setVersion(goodsMapper.getVersionByGoodsId(goodsId));
                res = goodsMapper.reduceStockByVersion(sg);
            }catch(Exception e){
                logger.error("version不一致");
                e.printStackTrace();
            }
            if(res != 0) break;
        }while(attempt < MAX_TRIES);
        return res > 0;
    }
}
