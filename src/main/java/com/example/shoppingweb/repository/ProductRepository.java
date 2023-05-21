package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    Product getProductById(Long productId);
    Optional<Product> findById(Long productId);
    Product findByName(String name);
    List<Product> findAll();
    List<Product> findByNameContainingIgnoreCase(String query);
    List<Product> findByNameContainingIgnoreCaseAndCategoryId(String query, Long categoryId);
}

