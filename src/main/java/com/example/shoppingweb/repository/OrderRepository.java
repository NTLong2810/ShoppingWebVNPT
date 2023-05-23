package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Order;
import com.example.shoppingweb.model.OrderDetail;
import com.example.shoppingweb.model.Seller;
import org.aspectj.weaver.ast.Or;
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

    List<Order> findBySeller(Seller seller);
    List<Order> findOrdersBySellerIdAndStatus(Long sellerId, String status);

}
