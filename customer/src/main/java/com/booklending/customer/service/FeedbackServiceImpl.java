package com.booklending.customer.service; 
 
import com.booklending.customer.dto.FeedbackRequestDto; 
import com.booklending.customer.entity.Customer; 
import com.booklending.customer.entity.Feedback; 
import com.booklending.customer.repository.FeedbackRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
@Service 
public class FeedbackServiceImpl implements FeedbackService{ 
 
    @Autowired 
    private CustomerService customerService; 
 
    @Autowired 
    private FeedbackRepository feedbackRepository; 
 
    @Override 
    public void saveFeedback(FeedbackRequestDto feedbackRequestDto) { 
        Customer customer = customerService.isCustomerPresent(feedbackRequestDto.getCustomerId()); 
        Feedback feedback = new Feedback(); 
        feedback.setFeedback(feedbackRequestDto.getFeedback()); 
        feedback.setBookId(feedbackRequestDto.getBookId()); 
        feedback.setCustomer(customer); 
        feedbackRepository.save(feedback); 
    } 
} 
