package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Feedback;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.service.HomeService;
import com.example.shoppingweb.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductDetailService detailService;
    @Autowired
    private HomeService homeService;
    @GetMapping("/product/{productId}")
    public String Detail(@PathVariable Long productId, Model model){
        Product product = detailService.getProductById(productId);
        List<Feedback> feedbackList = detailService.getFeedbackByProduct(productId);
        List<Product> productList = homeService.findAll();
        model.addAttribute("listproduct",productList);
        model.addAttribute("product",product);
        model.addAttribute("feedbacks",feedbackList);
        return "product_detail";
    }
}
