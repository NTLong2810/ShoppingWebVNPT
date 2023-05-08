package com.example.shoppingweb.api;

import com.example.shoppingweb.model.CartDetail;
import com.example.shoppingweb.model.Order;
import com.example.shoppingweb.model.OrderDetail;
import com.example.shoppingweb.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/order/{customerid}")
    public String Order(@PathVariable("customerid") Long customerid, Model model, HttpSession session){
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }
        // Lấy danh sách Order của khách hàng từ cơ sở dữ liệu
        List<Order> orders = orderRepository.findByCustomerId(customerid);

        // Lấy danh sách OrderDetail từ danh sách Order
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            orderDetails.addAll(order.getOrderDetails());
        }

        // Đưa danh sách OrderDetail và Order vào model để hiển thị trên trang order
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orders", orders);
        return "order";
    }
}
