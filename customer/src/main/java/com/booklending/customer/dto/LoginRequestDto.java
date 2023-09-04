package com.booklending.customer.dto; 
 
public class LoginRequestDto { 
 
    private Long mobileNumber; 
    private String password; 
 
    public Long getMobileNumber() { 
        return mobileNumber; 
    } 
 
    public void setMobileNumber(Long mobileNumber) { 
        this.mobileNumber = mobileNumber; 
    } 
 
    public String getPassword() { 
        return password; 
    } 
 
    public void setPassword(String password) { 
        this.password = password; 
    } 
} 
