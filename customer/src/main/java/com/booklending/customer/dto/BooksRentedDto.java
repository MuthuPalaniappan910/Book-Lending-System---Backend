package com.booklending.customer.dto; 
 
import java.time.LocalDate; 
 
public class BooksRentedDto { 
     
    private Long fromCustomerId; 
    private LocalDate startDate; 
    private LocalDate endDate; 
    private String title; 
 
    public Long getFromCustomerId() { 
        return fromCustomerId; 
    } 
 
    public void setFromCustomerId(Long fromCustomerId) { 
        this.fromCustomerId = fromCustomerId; 
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