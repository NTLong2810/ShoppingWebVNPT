package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.ProductRepository;
import com.example.shoppingweb.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductListController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/product-list")
    public String ShowListProduct(Model model){
        //Lấy ra danh sách sản phẩm
        List<Product> productList = productRepository.findAll();
        model.addAttribute("listproduct", productList);
        return "product-list";
    }
}
