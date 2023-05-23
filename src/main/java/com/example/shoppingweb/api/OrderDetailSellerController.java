package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Order;
import com.example.shoppingweb.model.OrderDetail;
import com.example.shoppingweb.repository.OrderDetailRepository;
import com.example.shoppingweb.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
public class OrderDetailSellerController {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order-detail-seller/{orderid}")
    public String getOrderDetailByOrderId(@PathVariable("orderid") Long orderId, Model model, HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("seller") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }

        // Lấy thông tin OrderDetail dựa vào OrderId
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        // Lấy thông tin Order
        Order order = orderRepository.findById(orderId).orElse(null);

        // Đưa thông tin OrderDetail và Order vào model để hiển thị trên trang orderdetail
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("order", order);
        return "orderdetailseller";
    }
}
