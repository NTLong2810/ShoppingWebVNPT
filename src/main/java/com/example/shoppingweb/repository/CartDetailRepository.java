package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Cart;
import com.example.shoppingweb.model.CartDetail;
import com.example.shoppingweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail,Long> {
    List<CartDetail> findByCart(Cart cart);
    CartDetail findByProductAndCart(Product product, Cart cart);
}
