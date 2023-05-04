package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Account;
import com.example.shoppingweb.model.Customer;
import com.example.shoppingweb.repository.AccountRepository;
import com.example.shoppingweb.repository.CustomerRepository;
import com.example.shoppingweb.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AccountController {
    @Autowired
    private AccountService service;
    @Autowired
    private AccountRepository repository;
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/checklogin")
    public String checkLogin(ModelMap model, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Optional<Account> optionalAccount= repository.findByUsername(username);
        if (service.checkLogin(username, password)) {

            Account account = optionalAccount.get();
            Customer customer = customerRepository.findCustomerByAccount(account);
            // Đăng nhập thành công
            session.setAttribute("customerid", customer.getId() );
            session.setAttribute("account",account);
            return "redirect:/home";
        } else {
            // Sai tên đăng nhập hoặc mật khẩu
            model.addAttribute("messError", "Wrong username or password");
            return "login";
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
//        session.removeAttribute("username");
        return "redirect:/login";
    }
}