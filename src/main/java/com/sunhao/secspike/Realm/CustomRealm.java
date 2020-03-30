package com.sunhao.secspike.Realm;

import com.sunhao.secspike.service.impl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  15:53
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


    /**
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("------身份认证---------");
//        1、从主题传过来的认证信息中获取用户名
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = (String)authenticationToken.getPrincipal();
//        2、通过用户名去数据库中取得认证
        String password = userService.getPasswordByUsername(username);
        if(password == null){
            return null;
        }
        System.out.println(password);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,getName());
        return simpleAuthenticationInfo;
    }
}