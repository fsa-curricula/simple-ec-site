package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // すべての商品を取得
    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // IDで商品を取得
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 商品を保存（作成・更新）
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 商品を削除
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
