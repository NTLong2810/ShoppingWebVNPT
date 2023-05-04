package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Cart;
import com.example.shoppingweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findById(Long cartId);
}
