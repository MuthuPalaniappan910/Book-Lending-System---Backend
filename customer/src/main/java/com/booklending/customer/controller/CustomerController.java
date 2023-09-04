package com.booklending.customer.controller;

import com.booklending.customer.dto.CommonResponse; 
import com.booklending.customer.dto.NewCustomerRequestDto; 
import com.booklending.customer.dto.DashboardResponseDto; 
import com.booklending.customer.dto.FeedbackRequestDto; 
import com.booklending.customer.dto.GetCustomerResponseDto; 
import com.booklending.customer.dto.LoginRequestDto;  
import com.booklending.customer.dto.UpdateCustomerProfileRequestDto; 
import com.booklending.customer.entity.Customer; 
import com.booklending.customer.exception.JwtException; 
import com.booklending.customer.service.BookApplicationService; 
import com.booklending.customer.service.CustomerService; 
import com.booklending.customer.service.FeedbackService; 
import com.booklending.customer.service.TokenService; 
import com.booklending.customer.utils.ApplicationConstants; 
import com.booklending.customer.utils.Claim; 
import jakarta.validation.Valid; 
import org.springframework.beans.BeanUtils; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestHeader; 
import org.springframework.web.bind.annotation.RestController; 
 
import java.util.Objects; 
 
@CrossOrigin 
@RestController 
public class CustomerController { 
 
    @Autowired 
    private CustomerService customerService; 
 
    @Autowired 
    private TokenService tokenService; 
 
    @Autowired 
    private BookApplicationService bookApplicationService; 
 
    @Autowired 
    private FeedbackService feedbackService; 
 
    @PostMapping("/register") 
    public ResponseEntity<CommonResponse> customerRegister(@Valid @RequestBody NewCustomerRequestDto newCustomerRequestDto) { 
        return new ResponseEntity<>(customerService.customerRegister(newCustomerRequestDto), HttpStatus.OK); 
    } 
 
    @PostMapping("/login") 
    public ResponseEntity<CommonResponse> customerLogin(@RequestBody LoginRequestDto loginRequestDto) { 
        return new ResponseEntity<>(customerService.customerLogin(loginRequestDto), HttpStatus.OK); 
    } 
 
    @PutMapping("/{customerId}/update") 
    public ResponseEntity<CommonResponse> customerProfileUpdate(@PathVariable Long customerId  , 
                                                                @RequestHeader (value = "x-auth-token") String jwtToken, 
                                                                @RequestBody UpdateCustomerProfileRequestDto updateCustomerProfileRequestDto) { 
        validateToken(jwtToken); 
        tokenService.validateToken(extractToken(jwtToken), Claim.UPDATE_PROFILE); 
        return new ResponseEntity<>(customerService.customerProfileUpdate(customerId, updateCustomerProfileRequestDto), HttpStatus.OK); 
    } 
 
    @GetMapping("/{customerId}/dashboard") 
    public ResponseEntity<DashboardResponseDto> getCustomerDashboard(@PathVariable Long customerId) { 
        DashboardResponseDto response = new DashboardResponseDto(); 
        DashboardResponseDto dashboardResponseDto = bookApplicationService.getCustomerDashboard(customerId); 
        if (Objects.isNull(dashboardResponseDto)) { 
            response.setCommonResponse(setCommonResponse(Boolean.FALSE, ApplicationConstants.EMPTY_DASHBOARD)); 
            return new ResponseEntity<>(response, HttpStatus.OK); 
        } 
        if (!(dashboardResponseDto.getBooksLent().isEmpty())) { 
            response.setBooksLent(dashboardResponseDto.getBooksLent()); 
        } 
        if (!(dashboardResponseDto.getBooksRent().isEmpty())) { 
            response.setBooksRent(dashboardResponseDto.getBooksRent()); 
        } 
        response.setCommonResponse(setCommonResponse(Boolean.TRUE, ApplicationConstants.EMPTY_STRING)); 
        return new ResponseEntity<>(response, HttpStatus.OK); 
    } 
 
    @PostMapping("/feedback") 
    public ResponseEntity<CommonResponse> saveFeedback(@RequestBody FeedbackRequestDto feedbackRequestDto) { 
        feedbackService.saveFeedback(feedbackRequestDto); 
        return new ResponseEntity<>(setCommonResponse(Boolean.TRUE, ApplicationConstants.FEEDBACK_SAVE_SUCCESSFULLY), HttpStatus.OK); 
    } 
 
    @GetMapping("/{customerId}") 
    public ResponseEntity<GetCustomerResponseDto> getCustomerDetails(@PathVariable Long customerId) { 
        Customer customer = customerService.isCustomerPresent(customerId); 
        GetCustomerResponseDto getCustomerResponseDto = new GetCustomerResponseDto(); 
        BeanUtils.copyProperties(customer, getCustomerResponseDto); 
        return new ResponseEntity<>(getCustomerResponseDto, HttpStatus.OK); 
    } 
 
    private String extractToken(String jwtToken) { 
        return jwtToken.substring(ApplicationConstants.BEARER.length()); 
    } 
 
    private void validateToken(String jwtToken) { 
        if (jwtToken.isEmpty()) { 
            throw new JwtException(ApplicationConstants.INVALID_TOKEN); 
        } 
    } 
 
    private CommonResponse setCommonResponse(Boolean result, String message) { 
        CommonResponse commonResponse = new CommonResponse(); 
        commonResponse.setMessage(message); 
        commonResponse.setResult(result); 
        return commonResponse; 
    } 
} 
