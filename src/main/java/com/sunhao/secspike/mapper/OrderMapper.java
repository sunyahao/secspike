package com.sunhao.secspike.mapper;

import com.sunhao.secspike.bean.Order;
import com.sunhao.secspike.bean.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  21:11
 */
@Mapper
public interface OrderMapper {

    @Select("select * from ss_order where user_id=#{userId} and goods_id = #{goodsId}")
    public OrderInfo getOrder(long userId, long goodsId);

    @Insert("insert into ss_order(order_id,user_id,goods_id) values(#{orderId},#{userId},#{goodsId})")
    public int insertOrder(Order order);

    @Insert("insert into ss_order_info(order_id,user_id,goods_id,status,create_time,price,goods_name) values(#{orderId},#{userId},#{goodsId},#{status},#{createTime},#{price},#{goodsName})")
    public int insertOrderInfo(OrderInfo orderInfo);

    @Select("select * from ss_order_info where user_id = #{userId}")
    public List<OrderInfo> getAllOrder(long userId);

    @Select("select status from ss_order_info where order_id = #{orderId}")
    public int getOrderStatus(String orderId);

    @Update("update ss_order_info set status = 2 where order_id = #{orderId}")
    public int updateOrderStatusToTwo(String orderId);

    @Update("update ss_order_info set status = 1 where order_id = #{orderId}")
    public int updateOrderStatusToOne(String orderId);

    @Select("select * from ss_order_info where order_id = #{orderId}")
    public OrderInfo getOrderDetail(String orderId);

    @Select("select order_id from ss_order where user_id = #{userId} and goods_id = #{goodsId}")
    public String getOrderId(long userId,long goodsId);
}
