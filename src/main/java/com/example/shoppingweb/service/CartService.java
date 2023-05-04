package com.example.shoppingweb.service;

import com.example.shoppingweb.model.CartDetail;

import java.util.List;

public interface CartService {
    List<CartDetail> findbyCartId(Long cartid);
}
