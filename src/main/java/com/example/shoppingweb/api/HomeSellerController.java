package com.example.shoppingweb.api;

import com.example.shoppingweb.repository.OrderRepository;
import com.example.shoppingweb.service.HomeSellerService;
import com.example.shoppingweb.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeSellerController {
    @Autowired
    private HomeSellerService homeSellerService;
    @Autowired
    private OrderRepository orderRepository;
    @RequestMapping("/homeseller")
    public String homeSeller(Model model, HttpSession session) {
        Long sellerId = (Long) session.getAttribute("sellerId");
        Long customerCount = orderRepository.countCustomersBySellerIdAndStatus(sellerId);
        double dailyRevenue = homeSellerService.calculateDailyRevenue();
        model.addAttribute("dailyRevenue", dailyRevenue);
        model.addAttribute("customerCount", customerCount);
        return "home_seller";
    }
}
