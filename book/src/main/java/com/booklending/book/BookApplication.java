package com.booklending.book; 
 
 
import com.booklending.book.utils.ApplicationConstants; 
import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration; 
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; 
import org.springframework.cloud.openfeign.EnableFeignClients; 
import org.springframework.scheduling.annotation.EnableScheduling; 
 
@EnableDiscoveryClient 
@EnableFeignClients(ApplicationConstants.PACKAGE_PATH) 
@EnableScheduling 
@SpringBootApplication (exclude={SecurityAutoConfiguration.class}) 
public class BookApplication { 
 
   public static void main(String[] args) { 
      SpringApplication.run(BookApplication.class, args); 
   } 
 
} 

