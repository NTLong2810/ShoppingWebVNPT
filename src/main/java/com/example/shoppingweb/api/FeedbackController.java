package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Customer;
import com.example.shoppingweb.model.Feedback;
import com.example.shoppingweb.model.OrderDetail;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.FeedbackRepository;
import com.example.shoppingweb.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class FeedbackController {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @PostMapping("/feedback")
    public String addFeedback(@RequestParam("orderdetailid") Long orderDetailId,
                              @RequestParam("orderid") Long orderId,
                              @RequestParam("rate") Integer rating,
                              @RequestParam("comment") String comment) {
        // lấy thông tin order detail, product và customer từ database
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);
        Product product = orderDetail.getProduct();
        Customer customer = orderDetail.getOrder().getCustomer();

        // tạo đối tượng feedback và set các giá trị
        Feedback feedback = orderDetail.getFeedback();
        if (feedback == null) {
            feedback = new Feedback();
            feedback.setOrderDetail(orderDetail);
            feedback.setProduct(product);
            feedback.setCustomer(customer);
        }
        feedback.setRating(rating);
        feedback.setComment(comment);
        feedback.setDate(new Date());

        // lưu feedback vào database
        feedback = feedbackRepository.save(feedback);

        // cập nhật feedback_id trong order detail
        orderDetail.setFeedback(feedback);
        orderDetailRepository.save(orderDetail);

        // chuyển hướng về trang chi tiết đơn hàng
        return "redirect:/orderdetail/" + orderId;
    }
}
