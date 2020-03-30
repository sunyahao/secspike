package com.sunhao.secspike.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/28  14:32
 */
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 存储值，将T类型的值自动转换为String类型并存储
     * @param prefix
     * @param key
     * @param val
     * @param <T>
     * @return val为null或值为空返回false否则返回true
     */
    public <T> Boolean set(KeyPrefix prefix, String key, T val){
        try(Jedis jedis = jedisPool.getResource()){
            String realKey = getRealKey(prefix,key);
            String str = beanToString(val);
            if(str == null || str.length() <=0) return false;
            int time = prefix.expireSeconds();
            if(time <= 0){
                jedis.set(realKey,str);
            } else{
                jedis.setex(realKey,time,str);
            }
            return true;
        }
    }

    /**
     * 获取redis实例
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key,Class<T> clazz){
        try(Jedis jedis = jedisPool.getResource()){
            String realKey = getRealKey(prefix,key);
            String str = jedis.get(realKey);
            return stringToBean(str,clazz);
        }
    }

    /**判断是否存在该key
     *
     * @return
     */
    public boolean exists(KeyPrefix prefix, String key){
        try(Jedis jedis = jedisPool.getResource()){
            String realKey = getRealKey(prefix,key);
            return jedis.exists(realKey);
        }
    }

    /**
     * 若该key存在则增加1，若不存在则初始化为0再返回1
     * @param prefix
     * @param key
     * @return 返回增加后的值
     */
    public Long incr(KeyPrefix prefix, String key){
        try(Jedis jedis = jedisPool.getResource()){
            String realKey = getRealKey(prefix,key);
            return jedis.incr(realKey);
        }
    }

    /**
     * 若key存在则减1，不存在则初始化为0，再减少1
     * @param prefix
     * @param key
     * @return 返回减少后的值
     */
    public Long decr(KeyPrefix prefix, String key){
        try(Jedis jedis = jedisPool.getResource()){
            String realKey = getRealKey(prefix,key);
            return jedis.decr(realKey);
        }
    }

    /**
     * 删除key
     * @param prefix
     * @param key
     * @return 删除成功返回true，反之返回false
     */
    public Boolean delete(KeyPrefix prefix, String key){
        try(Jedis jedis = jedisPool.getResource()){
            String realKey = getRealKey(prefix,key);
            Long l = jedis.del(realKey);
            return l>0;
        }
    }


    /**
     * 将bean转换为String类型存入redis中
     * @param val
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T val){
        if(val == null) return null;
        Class<?> clazz = val.getClass();//获得val的类型
        if(clazz == int.class || clazz == Integer.class){
            return String.valueOf(val);
        } else if(clazz == Long.class || clazz == long.class){
            return String.valueOf(val);
        } else if(clazz == String.class){
            return (String)val;
        } else{
            return JSON.toJSONString(val);
        }
    }

    /**
     * 封装了redis中取回实例时将字符类型转换为存入时的bean的类型
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str,Class<T> clazz){
        if(str == null || str.length() <= 0 || clazz == null){
            return null;
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(str);
        } else if(clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(str);
        } else if(clazz == String.class){
            return (T) str;
        } else{
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    private String getRealKey(KeyPrefix prefix,String key){
        String str = prefix.getPrefix();
        return str+":"+key;
    }
}
