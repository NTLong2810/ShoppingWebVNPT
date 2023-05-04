package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Feedback;
import com.example.shoppingweb.model.Product;

import java.util.List;

public interface ProductDetailService {
    Product getProductById(Long productid);
    List<Feedback> getFeedbackByProduct(Long productid);
}
