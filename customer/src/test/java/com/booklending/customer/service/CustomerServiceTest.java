package com.booklending.customer.service; 
 
import com.booklending.customer.dto.CommonResponse; 
import com.booklending.customer.dto.NewCustomerRequestDto; 
import com.booklending.customer.dto.LoginRequestDto; 
import com.booklending.customer.dto.UpdateCustomerProfileRequestDto; 
import com.booklending.customer.entity.Customer; 
import com.booklending.customer.repository.CustomerRepository; 
import com.booklending.customer.utils.ApplicationConstants; 
import org.junit.jupiter.api.Assertions; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.Mockito; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.mockito.junit.jupiter.MockitoSettings; 
import org.mockito.quality.Strictness; 
import org.springframework.security.crypto.password.PasswordEncoder; 
 
@ExtendWith(MockitoExtension.class) 
@MockitoSettings(strictness = Strictness.LENIENT) 
public class CustomerServiceTest { 
 
    @InjectMocks 
    private CustomerServiceImpl customerService; 
    @Mock 
    private CustomerRepository customerRepository; 
    @Mock 
    private PasswordEncoder passwordEncoder; 
 
    private final static Long CUSTOMER_ID = 1L; 
    private final static String INCORRECT_PASSWORD = "incorrect"; 
    private final static String HASHED_PASSWORD = "hashed_password"; 
 
    NewCustomerRequestDto newCustomerRequestDto; 
    CommonResponse commonResponse; 
    LoginRequestDto loginRequestDto; 
    UpdateCustomerProfileRequestDto updateCustomerProfileRequestDto; 
    Customer customer; 
 
    @BeforeEach 
    public void before() { 
        newCustomerRequestDto = new NewCustomerRequestDto(); 
        newCustomerRequestDto.setCustomerName("MSD"); 
        newCustomerRequestDto.setAddress("King Street"); 
        newCustomerRequestDto.setPassword("CaptainMS"); 
        newCustomerRequestDto.setCity("Ranchi"); 
        newCustomerRequestDto.setMobileNumber(9876543210L); 
        newCustomerRequestDto.setState("Jharkand"); 
 
        loginRequestDto = new LoginRequestDto(); 
        loginRequestDto.setMobileNumber(9876543210L); 
        loginRequestDto.setPassword("CaptainMS"); 
 
        updateCustomerProfileRequestDto = new UpdateCustomerProfileRequestDto(); 
        updateCustomerProfileRequestDto.setAddress("King Street"); 
        updateCustomerProfileRequestDto.setCity("Chennai"); 
        updateCustomerProfileRequestDto.setState("TN"); 
 
        customer = new Customer(); 
        customer.setCustomerName("MSD"); 
        customer.setAddress("King Street"); 
        customer.setPassword("CaptainMS"); 
        customer.setCity("Ranchi"); 
        customer.setMobileNumber(9876543210L); 
        customer.setState("Jharkand"); 
 
        commonResponse = new CommonResponse(); 
        commonResponse.setResult(Boolean.TRUE); 
    } 
 
    @Test 
    public void testCustomerRegister() { 
        //Given 
        commonResponse.setMessage(ApplicationConstants.CUSTOMER_REGISTERED_SUCCESSFULLY); 
        Mockito.when(passwordEncoder.encode(newCustomerRequestDto.getPassword())).thenReturn(HASHED_PASSWORD); 
        Mockito.when(customerRepository.save(customer)).thenReturn(customer); 
 
        //When 
        CommonResponse response = customerService.customerRegister(newCustomerRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response); 
    } 
 
    @Test 
    public void testCustomerLogin_CustomerNotFound() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.CUSTOMER_REGISTRATION_MESSAGE); 
        Mockito.when(customerRepository.findByMobileNumber(loginRequestDto.getMobileNumber())).thenReturn(null); 
 
        //When 
        CommonResponse response = customerService.customerLogin(loginRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response); 
    } 
 
    @Test 
    public void testCustomerLogin_InvalidLogin() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.INCORRECT_CREDENTIALS); 
        Mockito.when(customerRepository.findByMobileNumber(loginRequestDto.getMobileNumber())).thenReturn(customer); 
        Mockito.when(passwordEncoder.matches(INCORRECT_PASSWORD, customer.getPassword())).thenReturn(Boolean.FALSE); 
 
        //When 
        CommonResponse response = customerService.customerLogin(loginRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response); 
    } 
 
    @Test 
    public void testCustomerLogin_Success() { 
        //Given 
        commonResponse.setMessage(ApplicationConstants.SUCCESSFUL_LOGIN); 
        Mockito.when(customerRepository.findByMobileNumber(loginRequestDto.getMobileNumber())).thenReturn(customer); 
        Mockito.when(passwordEncoder.matches(loginRequestDto.getPassword(), customer.getPassword())).thenReturn(Boolean.TRUE); 
 
        //When 
        CommonResponse response = customerService.customerLogin(loginRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response); 
    } 
 
    @Test 
    public void testCustomerProfileUpdate_CustomerNotFound() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.CUSTOMER_NOT_FOUND); 
        Mockito.when(customerRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(null); 
 
        //When 
        CommonResponse response = customerService.customerProfileUpdate(CUSTOMER_ID, updateCustomerProfileRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response); 
    } 
 
    @Test 
    public void testCustomerProfileUpdate_Success() { 
        //Given 
        commonResponse.setMessage(ApplicationConstants.CUSTOMER_PROFILE_UPDATED_SUCCESSFULLY); 
        Mockito.when(customerRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(customer); 
 
        //When 
        CommonResponse response = customerService.customerProfileUpdate(CUSTOMER_ID, updateCustomerProfileRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response); 
    } 
 
    private void validateAssertions(CommonResponse commonResponse, CommonResponse response) {  
        Assertions.assertEquals(commonResponse.getResult(), response.getResult()); 
    } 
} 