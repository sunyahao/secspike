package com.sunhao.secspike.controller;

import com.sunhao.secspike.api.CodeMsg;
import com.sunhao.secspike.api.Result;
import com.sunhao.secspike.bean.OrderInfo;
import com.sunhao.secspike.bean.User;
import com.sunhao.secspike.service.OrderService;
import com.sunhao.secspike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/4/6  18:39
 */
@RequestMapping("/order")
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/getMyOrder")
    public String getOrder(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<OrderInfo> orderList = orderService.getAllOrder(Long.valueOf(user.getUsername()));
        model.addAttribute("orderList",orderList);
        return "my_orders";
    }

    @GetMapping("/getOrderTail")
    public String getOrderDetail(Model model, HttpSession session, @RequestParam("goodsId")long goodsId){
        User user1 = (User) session.getAttribute("user");
        User user = userService.getUserInfo(user1.getUsername());
        String orderId = orderService.getOrderId(Long.valueOf(user1.getUsername()),goodsId);
        OrderInfo orderInfo = orderService.getOrderDetail(orderId);
        model.addAttribute("user",user);
        model.addAttribute("order",orderInfo);
        return "order_detail";
    }

    @PostMapping("/pay")
    @ResponseBody
    public <T> Result<T> orderPay(@RequestParam("orderId")String orderId){
        boolean flag = orderService.payForOrder(orderId);
        if(flag){
            return Result.success(CodeMsg.SUCCESS);
        }
        return Result.error(CodeMsg.PAY_FAIL);
    }
}
