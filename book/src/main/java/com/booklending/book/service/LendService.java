package com.booklending.book.service;

import com.booklending.book.dto.PurchaseRequestDto; 
import com.booklending.book.dto.DashboardResponseDto; 
import com.booklending.book.entity.Book; 
 
import java.time.LocalDate; 
 
public interface LendService { 
	
    DashboardResponseDto getCustomerDashboard(Long customerId); 
 
    String addCart(PurchaseRequestDto purchaseRequestDto); 
 
    LocalDate renewBook(Long lendId); 
 
    void sendNotificationsToCustomers(); 
 
    void deleteBookLend(Book book); 
} 

