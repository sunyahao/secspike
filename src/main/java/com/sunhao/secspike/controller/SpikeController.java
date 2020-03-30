package com.sunhao.secspike.controller;

import com.sunhao.secspike.api.CodeMsg;
import com.sunhao.secspike.api.Result;
import com.sunhao.secspike.bean.Order;
import com.sunhao.secspike.bean.User;
import com.sunhao.secspike.rabbitmq.MQSender;
import com.sunhao.secspike.rabbitmq.OrderMessage;
import com.sunhao.secspike.redis.GoodsKey;
import com.sunhao.secspike.redis.OrderKey;
import com.sunhao.secspike.redis.RedisService;
import com.sunhao.secspike.service.GoodsService;
import com.sunhao.secspike.service.OrderService;
import com.sunhao.secspike.vo.GoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  14:49
 */
@Controller
public class SpikeController {

    private final static Logger log = LoggerFactory.getLogger(SpikeController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    //本地标记
    private HashMap<Long,Boolean> localOverMap = new HashMap<>();

    @RequestMapping("/do_spike")
    public Result<Integer> doSpike(Model model, HttpSession session, @RequestParam("goodsId") long goodsId){
        User user = (User) session.getAttribute("user");
        if(user == null) return Result.error(CodeMsg.SESSION_ERROR);
        //获取该商品的内存标记，true表示已经秒杀完毕
        boolean over = localOverMap.get(goodsId);
        if(over){
            return Result.error(CodeMsg.SECSPIKE_OVER);
        }
        //该商品还未被秒杀完毕或还未初始化

        //判断商品是否售罄
        long stockCount = redisService.decr(GoodsKey.getGoodsStock,""+goodsId);
        /**
         * 小于0有两种情况：
         * 1、未被初始化，该商品数量还未被存储到redis中，所以下一步要进行初始化，初始化后如果大于0说明该商品的确是未被初始化造成的小于0
         * 2、该商品库存为0，这时候进行初始化时获得的商品列表中不包括该商品(会被canKill过滤)，因此在进行redis的decr操作时商品数量仍是小于0并且
         *    localOverMap仍是false,表示该商品已经售罄需要标记
         * 3、重新初始化，预减库存会造成缓存中的库存数量小于等于实际库存中的数量，原因：用户a多次点击抢购会造成多次预减库存而实际库存不变，
         *    因此当缓存中的库存为0时要将数据库中的数据与之同步
         */
        if(stockCount < 0){
            //假定未被初始化，初始化一次，如果该商品已被初始化过那么initialInfo()不会对该商品信息造成影响
            initialInfo();
            long stockCount2 = redisService.decr(GoodsKey.getGoodsStock,""+goodsId);
            //此时仍小于0说明该商品的确已售罄
            if(stockCount2 < 0){
                localOverMap.put(goodsId,true);
                return Result.error(CodeMsg.SECSPIKE_OVER);
            }
        }

        //判断用户是否重复秒杀
        if(redisService.exists(OrderKey.getSpikeOrderByUserAndGoods,""+goodsId+user.getUsername())){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        Order order = orderService.getOrder(Long.valueOf(user.getUsername()),goodsId);
        if(order != null){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }

        //将用户id和商品id送入队列中
        OrderMessage message = new OrderMessage();
        message.setGoodsId(goodsId);
        message.setUserId(Long.valueOf(user.getUsername()));
        MQSender sender = new MQSender();
        sender.sendOrderMessage(message);
        return Result.success(CodeMsg.SECSPIKE_WAIT);
    }

    /**
     * 1、初始化信息
     * 2、获取当前还可以继续进行秒杀的商品列表并将其标记到localOverMap和存储到redis中
     */
    public void initialInfo(){
        //获取当前还可以继续进行秒杀的商品列表
        List<GoodsVO> list = goodsService.getGoodsList();
        if(list == null) return ;
        for(GoodsVO goods:list){
            redisService.set(GoodsKey.getGoodsStock,String.valueOf(goods.getGoodsId()),goods.getStockCount());
            //false表示该id号的商品当前还有库存
            localOverMap.put(goods.getGoodsId(),false);
        }
    }
}
