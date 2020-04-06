package com.sunhao.secspike.config;

import com.sunhao.secspike.Realm.CustomRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  15:43
 */
@Configuration
public class ShiroConfig {
    /**
     *
     * @return
     */
    //将自己的验证方式加入容器
    @Bean
    public CustomRealm myShiroRealm(){
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    //权限管理，将realm配置进securityManager中

    /**
     *
     * @return
     */
    @Bean
    public DefaultSecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myShiroRealm());
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //setLoginUrl如果缺省为Web工程下根目录中的"/login.jsp"页面或"/login"映射
        shiroFilterFactoryBean.setLoginUrl("/to_login");
        //设置拦截器
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        //开放匿名权限
        filterChainDefinitionMap.put("/do_login","anon");
        filterChainDefinitionMap.put("/do_spike1","anon");
        //其余接口一律拦截
        //这一行代码必须放到最后，不然会导致所有url被拦截
        filterChainDefinitionMap.put("/**","authc");

        //设置filterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}
