package com.booklending.customer.service; 
 
 
public interface TokenService { 
	
    String getToken(Long customerId); 
 
    String validateToken(String validateAndExtractToken, String updateProfile); 
} 
