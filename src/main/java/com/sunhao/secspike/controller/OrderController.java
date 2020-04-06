package com.sunhao.secspike.controller;

import com.sunhao.secspike.bean.OrderInfo;
import com.sunhao.secspike.bean.User;
import com.sunhao.secspike.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/getMyOrder")
    public String getOrder(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<OrderInfo> orderList = orderService.getAllOrder(Long.valueOf(user.getUsername()));
        model.addAttribute("orderList",orderList);
        return "my_orders";
    }
}
