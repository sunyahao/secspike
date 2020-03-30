package com.sunhao.secspike.service;

import com.sunhao.secspike.bean.User;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  18:14
 */
public interface UserService {

    String getPasswordByUsername(String username);

    User getUserInfo(String username);
}
