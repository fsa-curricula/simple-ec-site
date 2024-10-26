package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.entity.Order;
import com.example.security.CustomUserDetails;
import com.example.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 注文履歴一覧表示
    @GetMapping
    public String listOrders(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<Order> orders = orderService.getOrdersByUser(userDetails.getUsername());
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    // 注文詳細表示
    @GetMapping("/{id}")
    public String showOrder(@PathVariable Long id, 
                            @AuthenticationPrincipal CustomUserDetails userDetails, 
                            Model model) {
        Order order = orderService.getOrderByIdAndUser(id, userDetails.getUsername())
                           .orElseThrow(() -> new IllegalArgumentException("無効な注文ID: " + id));
        model.addAttribute("order", order);
        return "orders/detail";
    }
}
