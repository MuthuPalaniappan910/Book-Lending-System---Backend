package com.booklending.customer.repository; 
 
import com.booklending.customer.entity.Customer; 
import org.springframework.data.jpa.repository.JpaRepository; 
 
public interface CustomerRepository extends JpaRepository<Customer, Long> { 
	
    Customer findByMobileNumber(Long mobileNumber); 
 
    Customer findByCustomerId(Long customerId); 
} 

 