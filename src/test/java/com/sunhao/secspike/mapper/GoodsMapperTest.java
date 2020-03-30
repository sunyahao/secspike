package com.sunhao.secspike.mapper;

import com.sunhao.secspike.vo.GoodsVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/28  20:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsMapperTest {

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void test1(){
        List<GoodsVO> list = goodsMapper.getGoodsList();
        for(GoodsVO s:list){
            System.out.println(s.getGoodsId());
        }
    }
}
