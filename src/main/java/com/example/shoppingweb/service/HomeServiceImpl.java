package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.CategoryRepository;
import com.example.shoppingweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HomeServiceImpl implements HomeService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public List<Category> findAllCate() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return (List<Product>) productRepository.findByCategoryId(categoryId);
    }
}
