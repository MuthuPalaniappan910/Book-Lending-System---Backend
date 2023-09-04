package com.booklending.customer.service; 
 
import com.booklending.customer.dto.FeedbackRequestDto; 
 
public interface FeedbackService { 
	
    void saveFeedback(FeedbackRequestDto feedbackRequestDto); 
} 
