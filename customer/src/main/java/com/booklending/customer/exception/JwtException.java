package com.booklending.customer.exception; 
 
public class JwtException extends RuntimeException{ 
 
    public JwtException(String message) 
    { 
        super(message); 
    } 
 
} 