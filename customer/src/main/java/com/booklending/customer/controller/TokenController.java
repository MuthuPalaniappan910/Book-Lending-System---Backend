package com.booklending.customer.controller; 
 
import com.booklending.customer.dto.CommonResponse; 
import com.booklending.customer.service.CustomerService; 
import com.booklending.customer.service.TokenService; 
import com.booklending.customer.utils.ApplicationConstants; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
 
@CrossOrigin 
@RequestMapping("/issue") 
@RestController 
public class TokenController { 
 
    @Autowired 
    private TokenService tokenService; 
 
    @Autowired 
    private CustomerService customerService; 
 
    @GetMapping ("/{customerId}") 
    public ResponseEntity<CommonResponse> getToken(@PathVariable Long customerId) { 
        Boolean isCustomerPresent = customerService.validateCustomer(customerId); 
        if(isCustomerPresent) { 
            return new ResponseEntity<>( 
                    setCommonResponse(Boolean.TRUE, tokenService.getToken(customerId)), HttpStatus.OK); 
        } 
        return new ResponseEntity<>( 
                setCommonResponse(Boolean.FALSE, ApplicationConstants.CUSTOMER_NOT_FOUND), HttpStatus.OK); 
    } 
 
    private CommonResponse setCommonResponse(Boolean result, String message) { 
        CommonResponse commonResponse = new CommonResponse(); 
        commonResponse.setMessage(message); 
        commonResponse.setResult(result); 
        return commonResponse; 
    } 
} 
