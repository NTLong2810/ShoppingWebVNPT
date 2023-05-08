package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Customer;
import com.example.shoppingweb.repository.CustomerRepository;
import com.example.shoppingweb.service.CartService;
import com.example.shoppingweb.model.CartDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class CheckOutController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerRepository repository;
    @PostMapping("/checkout")
    public String checkout(@RequestParam("cartid") Long cartId, @RequestParam("totalPrice") Double totalPrice,Model model, HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }
        // retrieve cart details from the database using the cartid
        List<CartDetail> cartDetails = cartService.findbyCartId(cartId);

        // add the cart details and total price to the model
        model.addAttribute("listcart", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        // return the checkout page
        return "checkout";
    }
}
