package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Account;
import com.example.shoppingweb.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
        Customer findCustomerByAccount(Account account);
}
