package com.booklending.customer.service; 
 
import com.booklending.customer.dto.FeedbackRequestDto; 
import com.booklending.customer.entity.Customer; 
import com.booklending.customer.entity.Feedback; 
import com.booklending.customer.repository.FeedbackRepository; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.Mockito; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.mockito.junit.jupiter.MockitoSettings; 
import org.mockito.quality.Strictness; 
 
@ExtendWith(MockitoExtension.class) 
@MockitoSettings(strictness = Strictness.LENIENT) 
public class FeedbackServiceTest { 
 
    @InjectMocks 
    private FeedbackServiceImpl feedbackService; 
 
    @Mock 
    private CustomerService customerService; 
 
    @Mock 
    private FeedbackRepository feedbackRepository; 
 
    FeedbackRequestDto feedbackRequestDto; 
 
    Customer customer; 
    Feedback feedback; 
 
    @BeforeEach 
    public void before() { 
        feedbackRequestDto = new FeedbackRequestDto(); 
        feedbackRequestDto.setFeedback("Good"); 
        feedbackRequestDto.setBookId(1L); 
        feedbackRequestDto.setCustomerId(1L); 
 
        customer = new Customer(); 
        customer.setCustomerName("MSD"); 
        customer.setCustomerId(1L); 
 
        feedback = new Feedback(); 
        feedback.setCustomer(customer); 
        feedback.setFeedbackId(1L); 
        feedback.setFeedback(feedbackRequestDto.getFeedback()); 
    } 
 
    @Test 
    public void testSaveFeedback() { 
        //Given 
        Mockito.when(customerService.isCustomerPresent(feedbackRequestDto.getCustomerId())).thenReturn(customer); 
        Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback); 
 
        //When 
        feedbackService.saveFeedback(feedbackRequestDto); 
    } 
} 
