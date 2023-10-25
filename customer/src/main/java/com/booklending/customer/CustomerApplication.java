package com.booklending.customer; 
 
import com.booklending.customer.utils.ApplicationConstants; 
import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration; 
import org.springframework.boot.context.properties.ConfigurationPropertiesScan; 
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; 
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients; 
import org.springframework.context.annotation.Bean; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
 
@ConfigurationPropertiesScan 
@EnableDiscoveryClient 
@EnableFeignClients (ApplicationConstants.PACKAGE_PATH) 
@EnableHystrix
@SpringBootApplication (exclude={SecurityAutoConfiguration.class}) 
public class CustomerApplication { 
 
   public static void main(String[] args) { 
      SpringApplication.run(CustomerApplication.class, args); 
   } 
 
   @Bean 
   public PasswordEncoder encoder() { 
      return new BCryptPasswordEncoder(); 
   } 
 
} 
