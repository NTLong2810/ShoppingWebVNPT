package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Cart;
import com.example.shoppingweb.model.CartDetail;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.CartDetailRepository;
import com.example.shoppingweb.repository.CartRepository;
import com.example.shoppingweb.repository.ProductRepository;
import com.example.shoppingweb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    private ProductRepository productRepository;

    private CartRepository cartRepository;

    private CartDetailRepository cartDetailRepository;

    private CartService cartService;
    @Autowired
    public CartController(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, CartService cartService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartService = cartService;
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productid") Long productId,
                            @RequestParam("cartid") Long cartId,
                            @RequestParam("accountid") Long accountId,
                            @RequestParam("quantity") int quantity, HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }
        // Kiểm tra tính hợp lệ của thông tin
        if (productId <= 0 || cartId <= 0 || accountId <= 0 || quantity <= 0) {
            return "redirect:/error"; // Chuyển hướng đến trang lỗi nếu thông tin không hợp lệ
        }

        // Lấy đối tượng Product từ productRepository bằng productId
        Product product = productRepository.findById(productId).orElse(null);
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (product == null) {
            return "redirect:/error"; // Chuyển hướng đến trang lỗi nếu không tìm thấy sản phẩm
        }

        // Lấy giá của sản phẩm từ Product và gán vào một biến price
        BigDecimal price = product.getPrice();

        // Tính giá tiền bằng cách nhân quantity từ request với price
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

        // Thực hiện insert vào bảng cart_detail
        CartDetail existingCartDetail = cartDetailRepository.findByProductAndCart(product, cart);
        if (existingCartDetail != null) {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
            int newQuantity = existingCartDetail.getQuantity() + quantity;
            BigDecimal newTotalPrice = price.multiply(BigDecimal.valueOf(newQuantity));
            existingCartDetail.setQuantity(newQuantity);
            existingCartDetail.setPrice(newTotalPrice);
            cartDetailRepository.save(existingCartDetail);
        } else {
            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới một CartDetail
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProduct(product);
            cartDetail.setCart(cart);
            cartDetail.setQuantity(quantity);
            cartDetail.setPrice(totalPrice);
            cartDetailRepository.save(cartDetail);
        }

        return "redirect:/product/"+ product.getId(); // Chuyển hướng về trang sản phẩm đang chọn 
    }
    @GetMapping("/cart/{cartid}")
    public String Cart(@PathVariable("cartid") Long cartid, Model model, HttpSession session){
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }
        List<CartDetail> cartDetailList = cartService.findbyCartId(cartid);

        //Lưu vào session
        session.setAttribute("listcart", cartDetailList);
        // Tính tổng giá trị của tất cả các đối tượng trong danh sách
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartDetail detail : cartDetailList) {
            totalPrice = totalPrice.add(detail.getPrice());
        }
        session.setAttribute("totalPrice", totalPrice);
        return "cart";
    }
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long id, HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }
        // Lấy cartid từ CartDetail cần xóa
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(id);
        if (!cartDetailOptional.isPresent()) {
            return "redirect:/error"; // Chuyển hướng đến trang lỗi nếu không tìm thấy CartDetail
        }
        Long cartId = cartDetailOptional.get().getCart().getId();

        // Xóa CartDetail theo id
        cartDetailRepository.deleteById(id);

        // Lấy lại danh sách CartDetail
        List<CartDetail> listCart = (List<CartDetail>) session.getAttribute("listcart");
        if (listCart != null) {
            for (CartDetail cartDetail : listCart) {
                if (cartDetail.getId().equals(id)) {
                    listCart.remove(cartDetail);
                    break;
                }
            }
        }

        return "redirect:/cart/" + cartId; // Chuyển hướng đến trang giỏ hàng
    }
}
