package com.sunhao.secspike.controller;

import com.alibaba.druid.util.StringUtils;
import com.sunhao.secspike.redis.GoodsKey;
import com.sunhao.secspike.redis.RedisService;
import com.sunhao.secspike.service.GoodsService;
import com.sunhao.secspike.service.SpikeGoodsService;
import com.sunhao.secspike.vo.GoodsVO;
import com.sunhao.secspike.vo.SprikeGoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/28  17:14
 */
@Api(tags = "商品接口")
@Controller
public class GoodsController {

    private final static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SpikeGoodsService spikeGoodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @ApiOperation(value = "获得要秒杀的商品列表")
    @RequestMapping(value = {"/to_list","/index"}, produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model){
        //取页面缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        List<GoodsVO> goodsList = goodsService.getGoodsList();
        log.info("待秒杀商品的列表",goodsList);
        model.addAttribute("goodsList", goodsList);

        //手动渲染
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);

        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @ApiOperation(value = "获得单个商品详情信息")
    @ApiImplicitParam(name = "goodsId", value="商品号")
    @RequestMapping(value="/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("goodsId") Long goodsId){
        //取页面缓存
        String html = redisService.get(GoodsKey.getGoodsDetail,goodsId.toString(),String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        SprikeGoodsVO sv = spikeGoodsService.getGoodsDetail(goodsId);
        model.addAttribute("goods",sv);
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,String.valueOf(goodsId),sv);
        }
        return html;
    }


}
