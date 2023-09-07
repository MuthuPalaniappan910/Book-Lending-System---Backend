package com.booklending.book.dto;

public class PurchaseRequestDto {
	
    private Long bookId; 
    private Long customerId; 
    private Integer numberOfDays; 
 
    public Long getBookId() { 
        return bookId; 
    } 
 
    public void setBookId(Long bookId) { 
        this.bookId = bookId; 
    } 
 
    public Long getCustomerId() { 
        return customerId; 
    } 
 
    public void setCustomerId(Long customerId) { 
        this.customerId = customerId; 
    } 
 
    public Integer getNumberOfDays() { 
        return numberOfDays; 
    } 
 
    public void setNumberOfDays(Integer numberOfDays) { 
        this.numberOfDays = numberOfDays; 
    } 


}
