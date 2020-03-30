package com.sunhao.secspike.controller;

import com.sunhao.secspike.api.CodeMsg;
import com.sunhao.secspike.api.Result;
import com.sunhao.secspike.bean.User;
import com.sunhao.secspike.redis.RedisService;
import com.sunhao.secspike.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  17:06
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    public <T> String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model){
        Result<T> res = null;
        UsernamePasswordToken token = null;
        try{
            //从SecurityUtils中创建一个subject
            Subject subject = SecurityUtils.getSubject();
            //在认证前准备好token令牌
             token = new UsernamePasswordToken(username,password);
            //执行认证登录
            subject.login(token);
        }catch (UnknownAccountException e){
            res = Result.error(CodeMsg.ACCOUNT_NOT_EXIST);
        }catch (DisabledAccountException e){
            res = Result.error(CodeMsg.ACCOUNT_DISABLED);
        }catch (IncorrectCredentialsException e){
            res = Result.error(CodeMsg.PASSWORD_ERROR);
        }catch (Exception e){
            res = Result.error(CodeMsg.SERVER_ERROR);
        }
        if (res == null){
            User user = userService.getUserInfo(username);
            session.setAttribute("user", user);
            return "redirect:/index";
        }else{
            model.addAttribute("result",res);
            return "login";
        }
    }
}
