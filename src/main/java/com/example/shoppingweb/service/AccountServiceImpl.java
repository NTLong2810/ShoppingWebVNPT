package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Account;
import com.example.shoppingweb.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repository;

    @Override
    public Account save(Account entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return repository.findByUsername(username);
    }
    @Override
    public void delete(Account entity) {
        repository.delete(entity);
    }

    @Override
    public boolean checkLogin(String username, String password) {
        Optional<Account> optionalAccount = repository.findByUsername(username);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (password.equals(account.getPassword())) {
                return true; // Đăng nhập thành công
            }
        }
        return false; // Sai tên đăng nhập hoặc mật khẩu
    }
}
