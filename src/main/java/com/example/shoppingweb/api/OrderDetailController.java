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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderDetailController {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/orderdetail/{orderid}")
    public String getOrderDetailByOrderId(@PathVariable("orderid") Long orderId, Model model, HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
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
        return "orderdetail";
    }
    @PostMapping("/cancel")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId, Model model) {
        // Tìm kiếm đơn hàng trong cơ sở dữ liệu
        Order order = orderRepository.findById(orderId).orElse(null);
            // Cập nhật trạng thái đơn hàng thành "Đang xử lý"
            order.setStatus("Đang xử lý");
            orderRepository.save(order);
        return "redirect:/orderdetail/" + orderId; // Chuyển hướng đến trang danh sách đơn hàng
    }
}