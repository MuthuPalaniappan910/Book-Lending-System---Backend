package com.booklending.customer.dto; 
 
public class FeedbackRequestDto { 
 
    private Long bookId; 
    private String feedback; 
    private Long customerId; 
 
    public Long getBookId() { 
        return bookId; 
    } 
 
    public void setBookId(Long bookId) { 
        this.bookId = bookId; 
    } 
 
    public String getFeedback() { 
        return feedback; 
    } 
 
    public void setFeedback(String feedback) { 
        this.feedback = feedback; 
    } 
 
    public Long getCustomerId() { 
        return customerId; 
    } 
 
    public void setCustomerId(Long customerId) { 
        this.customerId = customerId; 
    } 
} 