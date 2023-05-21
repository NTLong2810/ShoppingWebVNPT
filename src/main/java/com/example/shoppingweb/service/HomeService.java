package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;

import java.util.List;

public interface HomeService {
    List<Product> findAll();
    List<Category> findAllCate();
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> searchProductsByName(String query);
    List<Product> searchProductsByNameAndCategory(String query,Long categoryId);
}
