package com.booklending.customer.dto; 
 
import java.time.LocalDate; 
 
public class BooksFurnishedDto { 
 
    private Long toCustomerId; 
    private LocalDate startDate; 
    private LocalDate endDate; 
    private String title; 
 
    public Long getToCustomerId() { 
        return toCustomerId; 
    } 
 
    public void setToCustomerId(Long toCustomerId) { 
        this.toCustomerId = toCustomerId; 
    } 
 
    public LocalDate getStartDate() { 
        return startDate; 
    } 
 
    public void setStartDate(LocalDate startDate) { 
        this.startDate = startDate; 
    } 
 
    public LocalDate getEndDate() { 
        return endDate; 
    } 
 
    public void setEndDate(LocalDate endDate) { 
        this.endDate = endDate; 
    } 
 
    public String getTitle() { 
        return title; 
    } 
 
    public void setTitle(String title) { 
        this.title = title; 
    } 
} 