package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Category;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductDetailService detailService;
    @Autowired
    private HomeService homeService;
    @GetMapping("/product/{productId}")
    public String Detail(@PathVariable Long productId, Model model, HttpSession session){
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }
        Product product = detailService.getProductById(productId);
        // Lấy ra list feed back tương ứng với sản phẩm
        List<Feedback> feedbackList = detailService.getFeedbackByProduct(productId);
        if(feedbackList.isEmpty()){
            model.addAttribute("messEmpty", "Chưa có bình luận nào");
        }else{
            model.addAttribute("feedbacks",feedbackList);
        }
        List<Product> productList = homeService.findAll();
        model.addAttribute("listproduct",productList);
        model.addAttribute("product",product);

        return "product_detail";
    }

}
