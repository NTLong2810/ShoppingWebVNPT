package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Account;
import com.example.shoppingweb.model.Customer;
import com.example.shoppingweb.model.Seller;
import com.example.shoppingweb.repository.AccountRepository;
import com.example.shoppingweb.repository.CustomerRepository;
import com.example.shoppingweb.repository.SellerRepository;
import com.example.shoppingweb.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AccountController {

    private AccountService service;

    private AccountRepository repository;

    private CustomerRepository customerRepository;

    private SellerRepository sellerRepository;
    @Autowired
    public AccountController(AccountService service, AccountRepository repository, CustomerRepository customerRepository, SellerRepository sellerRepository) {
        this.service = service;
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRepository;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/checklogin")
    public String checkLogin(ModelMap model, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Optional<Account> optionalAccount= repository.findByUsername(username);
        if (service.checkLogin(username, password)) {

            Account account = optionalAccount.get();
            session.setAttribute("account",account);
            //Lặp qua các role trong account lấy tên
            String role = account.getRoles().iterator().next().getName();
            if (role.equals("CUSTOMER")) {
                Customer customer = customerRepository.findCustomerByAccount(account);
                // Đăng nhập thành công
                session.setAttribute("customer", customer);
                return "redirect:/home";
            } else if (role.equals("SELLER")) {
                Seller seller = sellerRepository.findSellerByAccount(account);
                session.setAttribute("seller", seller);
                session.setAttribute("sellerId", seller.getId());
                // Trang web tương ứng của SELLER
                return "redirect:/homeseller";
            } else if (role.equals("ADMIN")) {
                // Trang web tương ứng của ADMIN
                return "redirect:/admin-page";
            }
        } else {
            // Sai tên đăng nhập hoặc mật khẩu
            model.addAttribute("messError", "Wrong username or password");
            return "login";
        }
        return null;
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
//        session.removeAttribute("username");
        return "redirect:/login";
    }
}