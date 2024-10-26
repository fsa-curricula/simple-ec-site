package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Favorite;
import com.example.entity.Product;
import com.example.entity.User;
import com.example.repository.FavoriteRepository;
import com.example.repository.ProductRepository;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductRepository productRepository;

    // ユーザーのお気に入り一覧を取得
    public List<Favorite> getFavoritesByUser(User user) {
        return favoriteRepository.findByUserOrderByCreatedAtDesc(user);
    }

    // ユーザーと商品のお気に入りを取得
    public Optional<Favorite> getFavoritesByUserAndProduct(User user, Product product) {
        return favoriteRepository.findByUserAndProduct(user, product);
    }

    // お気に入りに追加
    public void addFavorite(User user, Long productId) throws Exception {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            throw new Exception("商品が存在しません。");
        }

        Product product = productOpt.get();

        // 既にお気に入りに登録されているか確認
        Optional<Favorite> existingFavorite = favoriteRepository.findByUserAndProduct(user, product);
        if (existingFavorite.isPresent()) {
            throw new Exception("既にお気に入りに登録されています。");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        favoriteRepository.save(favorite);
    }

    // お気に入りから削除
    public void removeFavorite(Long favoriteId, User user) throws Exception {
        Optional<Favorite> favoriteOpt = favoriteRepository.findById(favoriteId);
        if (!favoriteOpt.isPresent()) {
            throw new Exception("お気に入りが存在しません。");
        }

        Favorite favorite = favoriteOpt.get();

        if (!favorite.getUser().equals(user)) {
            throw new Exception("他のユーザーのお気に入りを削除することはできません。");
        }

        favoriteRepository.delete(favorite);
    }

    // お気に入りの存在を確認
    public boolean isFavorite(User user, Product product) {
        return favoriteRepository.findByUserAndProduct(user, product).isPresent();
    }
}
