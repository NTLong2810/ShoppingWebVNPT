package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Account;

import java.util.Optional;

public interface AccountService {

    Account save(Account entity);
    Optional<Account> findByUsername(String username);

    void delete(Account entity);
    boolean checkLogin(String username, String password);
}
