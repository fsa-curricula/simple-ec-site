package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Favorite;
import com.example.entity.Product;
import com.example.entity.User;
import com.example.security.CustomUserDetails;
import com.example.service.FavoriteService;
import com.example.service.ProductService;
import com.example.service.PurchaseService;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private PurchaseService purchaseService;
    
    @Autowired
    private ProductService productService;
    
    

    // お気に入り一覧表示
    @GetMapping
    public String listFavorites(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = purchaseService.getUserByEmail(userDetails.getUsername());
        List<Favorite> favorites = favoriteService.getFavoritesByUser(user);
        model.addAttribute("favorites", favorites);
        return "favorites/list";
    }

    // お気に入りに追加
    @PostMapping("/add")
    public String addToFavorites(@RequestParam Long productId, 
                                 @AuthenticationPrincipal CustomUserDetails userDetails, 
                                 Model model) {
        User user = purchaseService.getUserByEmail(userDetails.getUsername());
        try {
            favoriteService.addFavorite(user, productId);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/products/" + productId;
    }

    // お気に入りから削除
    @PostMapping("/remove/{id}")
    public String removeFromFavorites(@PathVariable Long id, 
                                      @AuthenticationPrincipal CustomUserDetails userDetails, 
                                      Model model) {
        User user = purchaseService.getUserByEmail(userDetails.getUsername());
        try {
            favoriteService.removeFavorite(id, user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/favorites";
    }

    // お気に入りの追加/削除
    @PostMapping("/toggle")
    public String toggleFavorite(@RequestParam Long productId, 
                                @AuthenticationPrincipal CustomUserDetails userDetails, 
                                Model model) {
        User user = purchaseService.getUserByEmail(userDetails.getUsername());
        try {
            Product product = productService.getProductById(productId)
                                .orElseThrow(() -> new IllegalArgumentException("無効な商品ID: " + productId));
            Optional<Favorite> existingFavorite = favoriteService.getFavoritesByUserAndProduct(user, product);
            if (existingFavorite.isPresent()) {
                favoriteService.removeFavorite(existingFavorite.get().getId(), user);
            } else {
                favoriteService.addFavorite(user, productId);
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/products/" + productId;
    }
}
