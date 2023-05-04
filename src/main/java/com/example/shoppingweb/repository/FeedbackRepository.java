package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Feedback;
import com.example.shoppingweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> getFeedbacksByProduct(Long productid);
    List<Feedback> findByProduct(Product product);
}
