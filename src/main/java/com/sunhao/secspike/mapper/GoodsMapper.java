package com.sunhao.secspike.mapper;


import com.sunhao.secspike.bean.Goods;
import com.sunhao.secspike.bean.SpikeGoods;
import com.sunhao.secspike.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/20  21:17
 */
@Mapper
public interface GoodsMapper {
    /**
     * 返回商品列表
     * 返回的canKill字段表示是否可秒杀
     * @return
     */
    @Select("select goods_name,goods_img,goods_id,stock_count,start_time,end_time,(CASE WHEN(now() BETWEEN start_time AND end_time AND stock_count > 0 ) THEN 1 ELSE 0 END) AS canKill from ss_goods_spike where isactive=1")
    public List<GoodsVO> getGoodsList();

    @Select("select stock_count from ss_goods_spike where goods_id = #{goodsId}")
    Integer getStockCount(@Param("goodsId") long goodsId);

    @Update("update ss_goods_spike set stock_count = stock_count-1,version = version+1 where goods_id = #{goodsId} and stock_count > 0 and version = #{version}")
    public int reduceStockByVersion(SpikeGoods sg);

    @Select("select version from ss_goods_spike where goods_id=#{goodsId}")
    public int getVersionByGoodsId(@Param("goodsId") long goodsId);

    @Select("select * from ss_goods_spike where goods_id = #{goodsId}")
    public Goods getGoodsByGoodsId(long goodsId);
}
