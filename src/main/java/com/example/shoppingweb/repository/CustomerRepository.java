package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Account;
import com.example.shoppingweb.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
        Customer findCustomerByAccount(Account account);
        Optional<Customer> findById(Long customerid);
}
