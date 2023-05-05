package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private HomeService homeService;
    @RequestMapping("/home")
    public String home(Model model) {
        //Lấy ra danh sách sản phẩm
        List<Product> productList = homeService.findAll();
        //Lấy ra danh sách phân loại
        List<Category> categoryList = homeService.findAllCate();
        model.addAttribute("products", productList);
        model.addAttribute("category", categoryList);
        return "home";
    }
    @GetMapping("/category/{categoryId}")
    public String productsByCategory(@PathVariable Long categoryId, Model model) {
        //Lấy ra danh sách sản phẩm dựa vào phân loại ( Filter )
        List<Product> products = homeService.getProductsByCategory(categoryId);
        List<Category> categoryList = homeService.findAllCate();
        model.addAttribute("products", products);
        model.addAttribute("category", categoryList);
        return "home";
    }
}
