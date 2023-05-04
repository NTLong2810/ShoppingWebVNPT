package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Feedback;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.FeedbackRepository;
import com.example.shoppingweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailServiceImp implements ProductDetailService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Override
    public Product getProductById(Long productid) {
        return productRepository.getProductById(productid);
    }

    @Override
    public List<Feedback> getFeedbackByProduct(Long productid) {
        Optional<Product> productOpt = productRepository.findById(productid);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            List<Feedback> feedbackList = feedbackRepository.findByProduct(product);
            return feedbackList;
        } else {
            return new ArrayList<Feedback>();
        }
    }
}
