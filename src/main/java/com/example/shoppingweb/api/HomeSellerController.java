package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Order;
import com.example.shoppingweb.model.OrderDetail;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.model.Seller;
import com.example.shoppingweb.repository.OrderRepository;
import com.example.shoppingweb.service.HomeSellerService;
import com.example.shoppingweb.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class HomeSellerController {
    @Autowired
    private HomeSellerService homeSellerService;
    @Autowired
    private OrderRepository orderRepository;
    @RequestMapping("/homeseller")
    public String homeSeller(Model model, HttpSession session) {
        // Lấy thông tin người bán từ session
        Seller seller = (Seller) session.getAttribute("seller");
        if (seller == null) {
            // Người bán chưa đăng nhập, xử lý tùy ý (ví dụ: chuyển hướng đến trang đăng nhập)
            return "redirect:/login";
        }

        Long sellerId = (Long) session.getAttribute("sellerId");
        List<Order> orders = orderRepository.findOrdersBySellerIdAndStatus(sellerId, "Đã giao hàng");

        // Tạo một Map để lưu trữ tổng doanh thu của từng sản phẩm
        Map<Product, BigDecimal> productRevenueMap = new HashMap<>();
        // Tạo một Map để lưu trữ số lượng đã bán của từng sản phẩm
        Map<Product, Integer> productSoldQuantityMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                Product product = orderDetail.getProduct();
                //Lấy doanh thu sản phẩm
                BigDecimal productRevenue = orderDetail.getPrice();
                //Lấy số lượng đã bán
                int soldQuantity = orderDetail.getQuantity();
                // Kiểm tra xem sản phẩm đã có trong Map hay chưa
                if (productRevenueMap.containsKey(product)) {
                    // Nếu đã có, cộng thêm doanh thu vào tổng doanh thu hiện tại
                    BigDecimal currentRevenue = productRevenueMap.get(product);
                    productRevenueMap.put(product, currentRevenue.add(productRevenue));
                } else {
                    // Nếu chưa có, thêm sản phẩm và doanh thu vào Map
                    productRevenueMap.put(product, productRevenue);
                }
                if (productSoldQuantityMap.containsKey(product)) {
                    // Nếu đã có, cộng thêm số lượng đã bán vào tổng số lượng hiện tại
                    int currentSoldQuantity = productSoldQuantityMap.get(product);
                    productSoldQuantityMap.put(product, currentSoldQuantity + soldQuantity);
                } else {
                    // Nếu chưa có, thêm sản phẩm và số lượng đã bán vào Map
                    productSoldQuantityMap.put(product, soldQuantity);
                }
            }
        }

        // Sắp xếp danh sách sản phẩm theo doanh thu giảm dần
        List<Map.Entry<Product, BigDecimal>> sortedProductRevenueList = new ArrayList<>(productRevenueMap.entrySet());
        Collections.sort(sortedProductRevenueList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Lấy top 5 sản phẩm hoặc toàn bộ danh sách nếu ít hơn 5
        int topProductsCount = Math.min(sortedProductRevenueList.size(), 5);
        List<Map.Entry<Product, BigDecimal>> topProducts = sortedProductRevenueList.subList(0, topProductsCount);
        model.addAttribute("productSoldQuantity", productSoldQuantityMap);
        model.addAttribute("topProducts", topProducts);
        Long customerCount = orderRepository.countCustomersBySellerIdAndStatus(sellerId);
        double dailyRevenue = homeSellerService.calculateDailyRevenue();
        model.addAttribute("dailyRevenue", dailyRevenue);
        model.addAttribute("customerCount", customerCount);
        return "home_seller";
    }
}
