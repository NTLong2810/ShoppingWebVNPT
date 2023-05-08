package com.example.shoppingweb.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(name = "status", nullable = false)
    private String status;
    @Column(name="delivered_date")
    private Date delivered_date;
    @Column(name="expected_start_date")
    private Date expected_start;
    @Column(name="expected_end_date")
    private Date expected_end;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // constructors, getters, and setters

    public Order() {
    }

    public Order(Long id, Customer customer, Seller seller, String status, Date delivered_date, Date expected_start, Date expected_end, Payment payment, Date createdAt, Date updatedAt, List<OrderDetail> orderDetails) {
        this.id = id;
        this.customer = customer;
        this.seller = seller;
        this.status = status;
        this.delivered_date = delivered_date;
        this.expected_start = expected_start;
        this.expected_end = expected_end;
        this.payment = payment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Date getDelivered_date() {
        return delivered_date;
    }

    public void setDelivered_date(Date delivered_date) {
        this.delivered_date = delivered_date;
    }

    public Date getExpected_start() {
        return expected_start;
    }

    public void setExpected_start(Date expected_start) {
        this.expected_start = expected_start;
    }

    public Date getExpected_end() {
        return expected_end;
    }

    public void setExpected_end(Date expected_end) {
        this.expected_end = expected_end;
    }
}
