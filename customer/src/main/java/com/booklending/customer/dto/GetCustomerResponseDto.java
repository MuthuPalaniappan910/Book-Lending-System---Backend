package com.booklending.customer.dto; 
 
public class GetCustomerResponseDto { 
 
    private Long customerId; 
    private Long mobileNumber; 
    private String customerName; 
 
    public Long getCustomerId() { 
        return customerId; 
    } 
 
    public void setCustomerId(Long customerId) { 
        this.customerId = customerId; 
    } 
 
    public Long getMobileNumber() { 
        return mobileNumber; 
    } 
 
    public void setMobileNumber(Long mobileNumber) { 
        this.mobileNumber = mobileNumber; 
    } 
 
    public String getCustomerName() { 
        return customerName; 
    } 
 
    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    } 
} 
