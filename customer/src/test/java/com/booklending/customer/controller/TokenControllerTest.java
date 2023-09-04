package com.booklending.customer.controller; 
 
import com.booklending.customer.dto.CommonResponse; 
import com.booklending.customer.service.CustomerService; 
import com.booklending.customer.service.TokenService; 
import com.booklending.customer.utils.ApplicationConstants; 
import org.junit.jupiter.api.Assertions; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.Mockito; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
 
@ExtendWith(MockitoExtension.class) 
public class TokenControllerTest { 
 
    @InjectMocks 
    private TokenController tokenController; 
 
    @Mock 
    private TokenService tokenService; 
 
    @Mock 
    private CustomerService customerService; 
 
    private final static Long CUSTOMER_ID = 1L; 
 
    CommonResponse commonResponse; 
 
    @BeforeEach 
    public void before () { 
        commonResponse = new CommonResponse(); 
    } 
 
    @Test 
    public void testGetToken_InvalidCustomer() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.CUSTOMER_NOT_FOUND); 
        Mockito.when(customerService.validateCustomer(CUSTOMER_ID)).thenReturn(Boolean.FALSE); 
 
        //When 
        ResponseEntity<CommonResponse> response = tokenController.getToken(CUSTOMER_ID); 
 
        //Then 
        validateAssertions(commonResponse, response); 
    } 
 
    @Test 
    public void testGetToken_Success() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.EMPTY_STRING); 
        Mockito.when(customerService.validateCustomer(CUSTOMER_ID)).thenReturn(Boolean.TRUE); 
        Mockito.when(tokenService.getToken(CUSTOMER_ID)).thenReturn(ApplicationConstants.EMPTY_STRING); 
 
        //When 
        ResponseEntity<CommonResponse> response = tokenController.getToken(CUSTOMER_ID); 
 
        //Then 
        validateAssertions(commonResponse, response); 
    } 
 
    private void validateAssertions(CommonResponse commonResponse, ResponseEntity<CommonResponse> response) { 
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()); 
        Assertions.assertEquals(commonResponse.getMessage(), response.getBody().getMessage()); 
        Assertions.assertEquals(commonResponse.getResult(), response.getBody().getResult()); 
    } 
} 