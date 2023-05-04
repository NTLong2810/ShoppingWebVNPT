package com.example.shoppingweb;

import com.example.shoppingweb.model.CartDetail;
import com.example.shoppingweb.service.CartService;
import com.example.shoppingweb.service.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ShoppingWeb {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingWeb.class, args);
    }
}
