package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Order;
import com.example.shoppingweb.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
@Service
public class HomeSellerServiceImpl implements HomeSellerService{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public double calculateDailyRevenue() {
        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        List<Order> orders = orderRepository.findByStatusAndDeliveredBetween("Đã giao hàng", startDate, endDate);
        double revenue = 0;
        for (Order order : orders) {
            revenue += order.getTotal().doubleValue();
        }
        return revenue;
    }
}
