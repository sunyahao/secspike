package com.sunhao.secspike.mapper;

import com.sunhao.secspike.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  18:17
 */
@Mapper
public interface UserMapper {

    @Select("select password from ss_user where username = #{username}")
    public String getPasswordByUsername(@Param("username") String username);

    @Select("select * from ss_user where username = #{username}")
    public User getUserInfo(String username);
}
