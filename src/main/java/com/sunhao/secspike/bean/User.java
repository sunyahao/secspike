package com.sunhao.secspike.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunhao on 2020/3/20.
 */
@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String place;
    private String name;
}
