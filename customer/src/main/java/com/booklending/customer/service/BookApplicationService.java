package com.booklending.customer.service; 
 
import com.booklending.customer.dto.DashboardResponseDto; 
import org.springframework.cloud.openfeign.FeignClient; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
 
@FeignClient (name = "book") 
public interface BookApplicationService { 
 
    @GetMapping("/{customerId}/dashboard") 
    DashboardResponseDto getCustomerDashboard(@PathVariable Long customerId); 
} 
