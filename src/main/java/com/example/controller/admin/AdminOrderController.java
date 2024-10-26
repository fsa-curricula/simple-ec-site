package com.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Order;
import com.example.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    // 注文管理画面表示（すべての注文）
    @GetMapping
    public String listAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders/list";
    }

    // 注文詳細表示
    @GetMapping("/{id}")
    public String showOrderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                           .orElseThrow(() -> new IllegalArgumentException("無効な注文ID: " + id));
        model.addAttribute("order", order);
        return "admin/orders/detail";
    }
}
