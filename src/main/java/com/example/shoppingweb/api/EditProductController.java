package com.example.shoppingweb.api;

import com.example.shoppingweb.model.*;
import com.example.shoppingweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class EditProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/editproduct/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model,HttpSession session) {
        // Lấy thông tin người bán từ session
        Seller seller = (Seller) session.getAttribute("seller");
        if (seller == null) {
            // Người bán chưa đăng nhập, xử lý tùy ý (ví dụ: chuyển hướng đến trang đăng nhập)
            return "redirect:/login";
        }

        Product product = productService.getProductById(id);
        if (product == null) {
            // Nếu sản phẩm không tồn tại, xử lý theo ý của bạn (ví dụ: hiển thị thông báo lỗi)
            return "error"; // Ví dụ: chuyển hướng đến trang thông báo lỗi
        }
        // Lấy lại danh sách nhà cung cấp, nhãn hiệu, và thể loại để hiển thị trong form
        List<Supplier> suppliers = productService.getAllSuppliers();
        List<Brand> brands = productService.getAllBrands();
        List<Category> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("brands", brands);
        model.addAttribute("product", product);
        return "editproduct"; // Trả về tên của file HTML cho trang chỉnh sửa sản phẩm
    }

    @PostMapping("/editproduct")
    public String editProduct(@RequestParam("id") Long id,
                              @RequestParam("image") String image,
                              @RequestParam("title") String title,
                              @RequestParam("price") BigDecimal price,
                              @RequestParam("description") String description,
                              @RequestParam("quantity") Integer quantity,
                              @RequestParam("supid") Long supid,
                              @RequestParam("brandid") Long brandid,
                              @RequestParam("cateid") Long cateid,
                               Model model) {

        Product existingProduct = productService.getProductByName(title);
        if (existingProduct != null) {
            // Nếu sản phẩm đã tồn tại và khác với sản phẩm đang chỉnh sửa, hiển thị thông báo lỗi
            Product product = productService.getProductById(id);
            model.addAttribute("errorMessage", "Sản phẩm đã tồn tại");
            // Lấy lại danh sách nhà cung cấp, nhãn hiệu, và thể loại để hiển thị trong form
            List<Supplier> suppliers = productService.getAllSuppliers();
            List<Brand> brands = productService.getAllBrands();
            List<Category> categories = productService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("suppliers", suppliers);
            model.addAttribute("brands", brands);
            model.addAttribute("product", product);
            // Trả về tên của file HTML cho trang chỉnh sửa sản phẩm
            return "editproduct";
        }

        Product product = productService.getProductById(id);
        if (product == null) {
            // Nếu sản phẩm không tồn tại, xử lý theo ý của bạn (ví dụ: hiển thị thông báo lỗi)
            return "error"; // Ví dụ: chuyển hướng đến trang thông báo lỗi
        }

        // Cập nhật thông tin sản phẩm
        product.setImage(image);
        product.setName(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setStock(quantity);
        Supplier supplier = productService.getSupplierById(supid);
        Brand brand = productService.getBrandById(brandid);
        Category category = productService.getCategoryById(cateid);
        product.setSupplier(supplier);
        product.setBrand(brand);
        product.setCategory(category);

        productService.saveProduct(product);

        return "redirect:/editproduct/" + product.getId();
    }

}