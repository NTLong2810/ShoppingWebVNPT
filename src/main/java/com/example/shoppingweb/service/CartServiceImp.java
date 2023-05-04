package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Cart;
import com.example.shoppingweb.model.CartDetail;
import com.example.shoppingweb.model.Feedback;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.CartDetailRepository;
import com.example.shoppingweb.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp implements CartService{
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Override
    public List<CartDetail> findbyCartId(Long cartid) {
        Optional<Cart> cartOpt = cartRepository.findById(cartid);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            List<CartDetail> cartDetailList = cartDetailRepository.findByCart(cart);
            return cartDetailList;
        } else {
            return new ArrayList<CartDetail>();
        }
    }
}
