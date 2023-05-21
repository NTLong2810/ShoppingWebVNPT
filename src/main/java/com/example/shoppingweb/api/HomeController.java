package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private HomeService homeService;
    @RequestMapping("/home")
    public String home(Model model, HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }
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
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        // Loại bỏ khoảng trắng từ query
        String cleanedQuery = query.trim().replaceAll("\\s+", " ");

        // Tìm kiếm sản phẩm theo tên và query
        List<Product> searchResults = homeService.searchProductsByName(cleanedQuery);

        // Lấy danh sách phân loại
        List<Category> categoryList = homeService.findAllCate();
        // Kiểm tra kết quả tìm kiếm
        if (searchResults.isEmpty()) {
            model.addAttribute("noResultsMessage", "Không tìm thấy sản phẩm phù hợp.");
        } else {
            model.addAttribute("products", searchResults);
        }
        model.addAttribute("category", categoryList);
        return "home";
    }
}
