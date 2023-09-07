package com.booklending.customer.entity; 
 
import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.JoinColumn; 
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.Table; 
 
@Entity 
@Table(name = "Feedback") 
public class Feedback { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column (name = "FEEDBACK_ID") 
    private Long feedbackId; 
 
    @Column (name = "FEEDBACK", nullable = false) 
    private String feedback; 
 
    @Column (name = "BOOK_ID", nullable = false) 
    private Long bookId; 
 
    @ManyToOne 
    @JoinColumn (name = "CUSTOMER_ID", nullable = false) 
    private Customer customer; 
 
    public Long getFeedbackId() { 
        return feedbackId; 
    } 
 
    public void setFeedbackId(Long feedbackId) { 
        this.feedbackId = feedbackId; 
    } 
 
    public String getFeedback() { 
        return feedback; 
    } 
 
    public void setFeedback(String feedback) { 
        this.feedback = feedback; 
    } 
 
    public Long getBookId() { 
        return bookId; 
    } 
 
    public void setBookId(Long bookId) { 
        this.bookId = bookId; 
    } 
 
    public Customer getCustomer() { 
        return customer; 
    } 
 
    public void setCustomer(Customer customer) { 
        this.customer = customer; 
    } 
} 
