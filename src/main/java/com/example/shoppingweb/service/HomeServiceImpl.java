package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.CategoryRepository;
import com.example.shoppingweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<Product> searchProductsByName(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
    @Override
    public List<Product> searchProductsByNameAndCategory(String query, Long categoryId) {
        if (categoryId != null && categoryId != 0) {
            // Tìm kiếm theo tên và thể loại
            return productRepository.findByNameContainingIgnoreCaseAndCategoryId(query, categoryId);
        } else {
            // Chỉ tìm kiếm theo tên
            return productRepository.findByNameContainingIgnoreCase(query);
        }
    }

    @Override
    public List<String> getSuggestions(String query) {
        // Lấy danh sách sản phẩm dựa trên query và trích xuất tên sản phẩm
        List<Product> searchResults = searchProductsByName(query);
        List<String> suggestions = new ArrayList<>();
        for (Product product : searchResults) {
            suggestions.add(product.getName());
        }
        return suggestions;
    }
}
