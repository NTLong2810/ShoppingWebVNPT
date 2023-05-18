package com.example.shoppingweb.api;

import com.example.shoppingweb.model.*;
import com.example.shoppingweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class AddProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/addproduct")
    public String ViewAddProductPage(Model model){
        List<Supplier> suppliers = productService.getAllSuppliers();
        List<Brand> brands = productService.getAllBrands();
        List<Category> categoríes = productService.getAllCategories();
        model.addAttribute("categories", categoríes);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("brands", brands);

        return "addproduct";
    }
    @PostMapping("/addproduct")
    public String addProduct(@RequestParam("image") String image,
                             @RequestParam("title") String title,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("description") String description,
                             @RequestParam("quantity") Integer quantity,
                             @RequestParam("supid") Long supid,
                             @RequestParam("brandid") Long brandid,
                             @RequestParam("cateid") Long cateid, HttpSession session, Model model){
        // Kiểm tra xem tên sản phẩm đã tồn tại trong product hay chưa
        Product existingProduct = productService.getProductByName(title);
        if (existingProduct != null) {
            // Nếu sản phẩm đã tồn tại, hiển thị thông báo lỗi
            model.addAttribute("errorMessage", "Sản phẩm đã tồn tại");
            // và chuyển hướng người dùng đến trang thông báo lỗi hoặc trang khác
            return "addproduct"; // Ví dụ: chuyển hướng đến trang thông báo lỗi
        }
        // Lấy thông tin đối tượng Supplier, Brand, và Category từ cơ sở dữ liệu
        Supplier supplier = productService.getSupplierById(supid);
        Brand brand = productService.getBrandById(brandid);
        Category category = productService.getCategoryById(cateid);

        // Lấy đối tượng Seller từ session
        Seller seller = (Seller) session.getAttribute("seller");

        // Tạo đối tượng Product và thiết lập thông tin
        Product product = new Product();
        product.setImage(image);
        product.setName(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setStock(quantity);
        product.setSupplier(supplier);
        product.setBrand(brand);
        product.setCategory(category);
        product.setSeller(seller);

        // Lưu sản phẩm vào cơ sở dữ liệu
        productService.saveProduct(product);


        // Redirect hoặc trả về trang thành công
        return "redirect:/login"; // Ví dụ: chuyển hướng về trang danh sách sản phẩm

    }
}
