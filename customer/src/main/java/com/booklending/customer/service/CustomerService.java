package com.booklending.customer.service; 
 
import com.booklending.customer.dto.CommonResponse; 
import com.booklending.customer.dto.LoginRequestDto; 
import com.booklending.customer.dto.NewCustomerRequestDto; 
import com.booklending.customer.dto.UpdateCustomerProfileRequestDto; 
import com.booklending.customer.entity.Customer; 
 
public interface CustomerService { 
	
    CommonResponse customerRegister(NewCustomerRequestDto newCustomerRequestDto); 
 
    CommonResponse customerLogin(LoginRequestDto loginRequestDto); 
 
    CommonResponse customerProfileUpdate(Long customerId, UpdateCustomerProfileRequestDto updateCustomerProfileRequestDto); 
 
    Boolean validateCustomer(Long customerId); 
 
    Customer isCustomerPresent (Long customerId); 
} 

 