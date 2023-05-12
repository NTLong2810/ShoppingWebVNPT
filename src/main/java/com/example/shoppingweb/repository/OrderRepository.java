package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatusAndDeliveredBetween(String status, Date startDate, Date endDate);
    @Query("SELECT COUNT(DISTINCT o.customer.id) FROM Order o WHERE o.seller.id = :sellerId AND o.status = 'Đã giao hàng'")
    Long countCustomersBySellerIdAndStatus(@Param("sellerId") Long sellerId);

}
