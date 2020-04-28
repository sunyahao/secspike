package com.sunhao.secspike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/20  14:43
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String h(){
        return "login";
    }
}
