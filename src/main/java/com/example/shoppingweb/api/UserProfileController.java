package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Account;
import com.example.shoppingweb.model.Customer;
import com.example.shoppingweb.repository.AccountRepository;
import com.example.shoppingweb.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UserProfileController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @RequestMapping("/user-profile")
        private String User(HttpSession session){
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session.getAttribute("customer") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }
    return "userprofile";
}
    @PostMapping("/edit-profile")
    private String EditUser(@RequestParam("customerid") Long customerid,
                            @RequestParam("customer_image") String customerImage,
                            @RequestParam("customer_name") String customerName,
                            @RequestParam("customer_phone") String customerPhone,
                            @RequestParam("customer_email") String customerEmail,
                            @RequestParam("customer_address") String customerAddress,
                            @RequestParam("customer_shipped_address") String customerShippedAddress, HttpSession session, Model model) {

        // tìm khách hàng theo customerid
        Optional<Customer> optionalCustomer = customerRepository.findById(customerid);
        if (!optionalCustomer.isPresent()) {
            // xử lý lỗi nếu không tìm thấy khách hàng
            return "error";
        }
        Customer customer = optionalCustomer.get();
        // cập nhật thông tin khách hàng từ các thẻ input
        customer.setImage(customerImage);
        customer.setName(customerName);
        customer.setPhone(customerPhone);
        Account account = customer.getAccount();
        List<Account> checkaccount = accountRepository.findAll();
        // Kiểm tra email đã tồn tại hay chưa (trừ tài khoản đang đăng nhập)
        if (account != null && account.getEmail() != null && !account.getEmail().equals(customerEmail)) {
            for (Account acc : checkaccount) {
                if (!acc.equals(account) && acc.getEmail().equals(customerEmail)) {
                    model.addAttribute("messEmail", "Email đã được sử dụng");
                    return "userprofile";
                }
            }
            account.setEmail(customerEmail);
        }
        customer.setAddress(customerAddress);
        if (customerShippedAddress != null && !customerShippedAddress.isEmpty()) {
            customer.setShipped_address(customerShippedAddress);
        }

        // lưu khách hàng vào database
        customerRepository.save(customer);
        session.setAttribute("customer", customer);
        session.setAttribute("account", account);
        // chuyển hướng về trang userprofile
        return "userprofile";
    }
}
