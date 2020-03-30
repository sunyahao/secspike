package com.sunhao.secspike.mapper;

import com.sunhao.secspike.bean.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:11
 */
@Mapper
public interface OrderMapper {

    @Select("select * from ss_order where user_id=#{userId} and goods_id = #{goodsId}")
    public Order getOrder(long userId, long goodsId);

    @Insert("insert into ss_order(order_id,user_id,goods_id) values(#{orderId},#{userId},#{goodsId})")
    public int insertOrder(Order order);
}
