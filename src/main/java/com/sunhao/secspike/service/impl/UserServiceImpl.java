package com.sunhao.secspike.service.impl;

import com.sunhao.secspike.bean.User;
import com.sunhao.secspike.mapper.UserMapper;
import com.sunhao.secspike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  18:14
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getPasswordByUsername(String username) {
        return userMapper.getPasswordByUsername(username);
    }

    @Override
    public User getUserInfo(String username) {
        return userMapper.getUserInfo(username);
    }
}
