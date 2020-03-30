package com.sunhao.secspike.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/28  15:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    private RedisService redisService;

    @Test
    public void test1(){
        try(Jedis jedis = jedisPool.getResource()){
            System.out.println(jedis.exists("first_key"));
        }
    }

    @Test
    public void test2(){
        try(Jedis jedis = jedisPool.getResource()){
            System.out.println(jedis.incr("forth_key"));
        }
    }

    @Test
    public void test3(){
        try(Jedis jedis = jedisPool.getResource()){
            System.out.println(jedis.set("second_key","-9").equals("OK"));
        }
    }

    @Test
    public void test4(){
        try(Jedis jedis = jedisPool.getResource()){
            System.out.println(jedis.del("fifth_key"));//delete之后返回1
            System.out.println(jedis.exists("second_key"));
        }
    }

    @Test
    public void test5(){
        try(Jedis jedis = jedisPool.getResource()){
            System.out.println(jedis.decr("seventh_key"));
            System.out.println(jedis.exists("seventh_key"));
        }
    }

    @Test
    public void test6(){
        try(Jedis jedis = jedisPool.getResource()){
//            redisService.set(GoodsKey.getGoodsList,"",1);
            System.out.println(redisService.get(GoodsKey.getGoodsList,"",String.class));
//            redisService.delete(GoodsKey.getGoodsList,"");
        }
    }
}
