package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.entity.User;
import com.example.service.PurchaseService;
import com.example.security.CustomUserDetails;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    // 1クリック購入処理
    @PostMapping("/1-click")
    public String oneClickPurchase(@RequestParam Long productId, 
                                   @AuthenticationPrincipal CustomUserDetails userDetails, 
                                   Model model) {
        User user = purchaseService.getUserByEmail(userDetails.getUsername());
        List<Long> productIdsList = new ArrayList<Long>();
        productIdsList.add(productId);
        
        try {
            purchaseService.processPurchase(user, productIdsList);
            return "purchase/complete";
        } catch (Exception e) {
            model.addAttribute("error", "購入処理中にエラーが発生しました: " + e.getMessage());
            return "products/detail";
        }
    }
}
