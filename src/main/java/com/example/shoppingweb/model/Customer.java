package com.example.shoppingweb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", unique = true)
    private Account account;
    private String name;

    private String address;

    private String phone;

    private String shipped_address;

    // constructors, getters, setters, and other methods

    public Customer() {
    }

    public Customer(Long id, Account account, String name, String address, String phone, String shipped_address) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.shipped_address = shipped_address;
    }

    public String getShipped_address() {
        return shipped_address;
    }

    public void setShipped_address(String shipped_address) {
        this.shipped_address = shipped_address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
