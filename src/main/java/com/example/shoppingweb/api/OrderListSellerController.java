package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Order;
import com.example.shoppingweb.model.OrderDetail;
import com.example.shoppingweb.model.Seller;
import com.example.shoppingweb.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderListSellerController {
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/orderlistseller")
    public String ShowOrderListSeller(HttpSession session, Model model) {
        // Lấy thông tin người bán từ session
        Seller seller = (Seller) session.getAttribute("seller");
        if (seller == null) {
            // Người bán chưa đăng nhập, xử lý tùy ý (ví dụ: chuyển hướng đến trang đăng nhập)
            return "redirect:/login";
        }

        // Lấy danh sách đơn hàng của người bán hiện tại
        List<Order> orders = orderRepository.findBySeller(seller);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            orderDetails.addAll(order.getOrderDetails());
        }

        // Đưa danh sách đơn hàng và chi tiết đơn hàng vào model để hiển thị trên trang
        model.addAttribute("orders", orders);
        model.addAttribute("orderDetails", orderDetails);

        return "order_list_seller";
    }
}
