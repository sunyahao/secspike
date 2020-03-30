package com.sunhao.secspike.mapper;

import com.sunhao.secspike.vo.SprikeGoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  12:19
 */
@Mapper
public interface SpikeGoodsMapper {

    @Select("select goods_id,goods_name,start_time,end_time,stock_count,spike_price,(CASE WHEN(now() BETWEEN start_time AND end_time AND stock_count > 0 ) THEN 1 ELSE 0 END) AS canKill from ss_goods_spike where goods_id = #{goodsId}")
    public SprikeGoodsVO getGoodsDetail(@Param("goodsId") long goodsId);
}
