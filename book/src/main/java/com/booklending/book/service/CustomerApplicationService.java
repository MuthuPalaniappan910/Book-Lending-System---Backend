package com.booklending.book.service;

import com.booklending.book.dto.GetCustomerResponseDto; 
import org.springframework.cloud.openfeign.FeignClient; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 

@FeignClient(name = "customer", url = "http://localhost:8801/customer") 
public interface CustomerApplicationService { 
 
    @GetMapping("/{customerId}") 
    GetCustomerResponseDto getCustomerDetails(@PathVariable Long customerId); 
} 

