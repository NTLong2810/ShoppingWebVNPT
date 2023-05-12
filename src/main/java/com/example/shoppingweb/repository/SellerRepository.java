package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Account;
import com.example.shoppingweb.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findSellerByAccount(Account account);
}
