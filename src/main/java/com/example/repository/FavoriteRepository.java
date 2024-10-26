package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Favorite;
import com.example.entity.User;
import com.example.entity.Product;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserOrderByCreatedAtDesc(User user);
    Optional<Favorite> findByUserAndProduct(User user, Product product);
}
