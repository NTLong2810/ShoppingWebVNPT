package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.model.Seller;
import com.example.shoppingweb.repository.ProductRepository;
import com.example.shoppingweb.service.HomeService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductListController {
    @Autowired
    private HomeService homeService;
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/product-list")
    public String ShowListProduct(Model model, HttpSession session){
        // Lấy thông tin người bán từ session
        Seller seller = (Seller) session.getAttribute("seller");
        if (seller == null) {
            // Người bán chưa đăng nhập, xử lý tùy ý (ví dụ: chuyển hướng đến trang đăng nhập)
            return "redirect:/login";
        }

        //Lấy ra danh sách sản phẩm
        List<Product> productList = productRepository.findAll();
        model.addAttribute("listproduct", productList);

        // Lấy danh sách thể loại sản phẩm
        List<Category> categoryList = homeService.findAllCate();
        model.addAttribute("categoryList", categoryList);
        return "product-list";
    }
    @GetMapping("/searchbyname")
    public String searchProducts(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "categoryId", required = false) Long categoryId, Model model) {
        // Loại bỏ khoảng trắng từ query
        String cleanedQuery = (query != null) ? query.trim().replaceAll("\\s+", " ") : "";

        // Tìm kiếm sản phẩm theo tên và thể loại (nếu có)
        List<Product> searchResults;

        if (categoryId != null && categoryId != 0) {
            if (!cleanedQuery.isEmpty()) {
                searchResults = homeService.searchProductsByNameAndCategory(cleanedQuery, categoryId);
            } else {
                searchResults = homeService.getProductsByCategory(categoryId);
            }
        } else {
            searchResults = homeService.searchProductsByName(cleanedQuery);
        }

        // Kiểm tra kết quả tìm kiếm
        if (searchResults.isEmpty()) {
            model.addAttribute("noResultsMessage", "Không tìm thấy sản phẩm phù hợp.");
        } else {
            model.addAttribute("listproduct", searchResults);
        }
        model.addAttribute("categoryId", categoryId);
        // Lấy lại danh sách thể loại để hiển thị trong view
        List<Category> categoryList = homeService.findAllCate();
        model.addAttribute("categoryList", categoryList);

        return "product-list";
    }

}
