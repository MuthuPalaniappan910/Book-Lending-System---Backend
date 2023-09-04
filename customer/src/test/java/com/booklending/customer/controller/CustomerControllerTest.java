package com.booklending.customer.controller; 
 
import com.booklending.customer.dto.BooksFurnishedDto; 
import com.booklending.customer.dto.BooksRentedDto; 
import com.booklending.customer.dto.CommonResponse; 
import com.booklending.customer.dto.GetCustomerResponseDto; 
import com.booklending.customer.dto.NewCustomerRequestDto; 
import com.booklending.customer.dto.DashboardResponseDto; 
import com.booklending.customer.dto.FeedbackRequestDto; 
import com.booklending.customer.dto.LoginRequestDto; 
import com.booklending.customer.dto.UpdateCustomerProfileRequestDto; 
import com.booklending.customer.entity.Customer; 
import com.booklending.customer.service.BookApplicationService; 
import com.booklending.customer.service.CustomerService; 
import com.booklending.customer.service.FeedbackService; 
import com.booklending.customer.service.TokenService; 
import com.booklending.customer.utils.ApplicationConstants; 
import com.booklending.customer.utils.Claim; 
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
import org.springframework.http.ResponseEntity; 
 
import java.time.LocalDate; 
import java.util.ArrayList; 
import java.util.List; 
 
@ExtendWith(MockitoExtension.class) 
@MockitoSettings(strictness = Strictness.LENIENT) 
public class CustomerControllerTest { 
 
    @InjectMocks 
    private CustomerController customerController; 
 
    @Mock 
    private CustomerService customerService; 
 
    @Mock 
    private BookApplicationService bookApplicationService; 
 
    @Mock 
    private TokenService tokenService; 
 
    @Mock 
    private FeedbackService feedbackService; 
 
    private final static Long CUSTOMER_ID = 1L; 
    private final static String TOKEN = "Bearer qwertyui"; 
 
    NewCustomerRequestDto newCustomerRequestDto; 
    CommonResponse commonResponse; 
    LoginRequestDto loginRequestDto; 
    BooksFurnishedDto booksFurnishedDto; 
    BooksRentedDto booksRentedDto; 
    UpdateCustomerProfileRequestDto updateCustomerProfileRequestDto; 
    DashboardResponseDto dashboardResponseDto; 
    List<BooksFurnishedDto> booksFurnishedDtoList; 
    List<BooksRentedDto> booksRentedDtoList; 
    FeedbackRequestDto feedbackRequestDto; 
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
 
        commonResponse = new CommonResponse(); 
        commonResponse.setResult(Boolean.TRUE); 
 
        booksFurnishedDto = new BooksFurnishedDto(); 
        booksFurnishedDto.setEndDate(LocalDate.now()); 
        booksFurnishedDto.setStartDate(LocalDate.now().minusDays(1)); 
        booksFurnishedDto.setTitle("ABC"); 
        booksFurnishedDto.setToCustomerId(2L); 
 
        booksFurnishedDtoList = new ArrayList<>(); 
        booksFurnishedDtoList.add(booksFurnishedDto); 
 
        booksRentedDto = new BooksRentedDto(); 
        booksRentedDto.setEndDate(LocalDate.now()); 
        booksRentedDto.setStartDate(LocalDate.now().minusDays(1)); 
        booksRentedDto.setTitle("ABC"); 
        booksRentedDto.setFromCustomerId(3L); 
 
        booksRentedDtoList = new ArrayList<>(); 
        booksRentedDtoList.add(booksRentedDto); 
 
        dashboardResponseDto = new DashboardResponseDto(); 
        dashboardResponseDto.setBooksRent(booksRentedDtoList); 
        dashboardResponseDto.setBooksLent(booksFurnishedDtoList); 
        dashboardResponseDto.setCommonResponse(commonResponse); 
 
        feedbackRequestDto = new FeedbackRequestDto(); 
        feedbackRequestDto.setFeedback("Good"); 
        feedbackRequestDto.setBookId(1L); 
        feedbackRequestDto.setCustomerId(1L); 
 
        customer = new Customer(); 
        customer.setCustomerName("MSD"); 
        customer.setAddress("King Street"); 
        customer.setPassword("CaptainMS"); 
        customer.setCity("Ranchi"); 
        customer.setMobileNumber(9876543210L); 
        customer.setState("Jharkand"); 
    } 
 
    @Test 
    public void testCustomerRegister() { 
        //Given 
        commonResponse.setMessage(ApplicationConstants.CUSTOMER_REGISTERED_SUCCESSFULLY); 
        Mockito.when(customerService.customerRegister(newCustomerRequestDto)).thenReturn(commonResponse); 
 
        //When 
        ResponseEntity<CommonResponse> response = customerController.customerRegister(newCustomerRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response.getBody()); 
    } 
 
    @Test 
    public void testCustomerLogin() { 
        //Given 
        commonResponse.setMessage(ApplicationConstants.SUCCESSFUL_LOGIN); 
        Mockito.when(customerService.customerLogin(loginRequestDto)).thenReturn(commonResponse); 
 
        //When 
        ResponseEntity<CommonResponse> response = customerController.customerLogin(loginRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response.getBody()); 
    } 
 
    @Test 
    public void testCustomerProfileUpdate() { 
        //Given 
        commonResponse.setMessage(ApplicationConstants.CUSTOMER_PROFILE_UPDATED_SUCCESSFULLY); 
        Mockito.when(customerService.customerProfileUpdate(CUSTOMER_ID, updateCustomerProfileRequestDto)).thenReturn(commonResponse); 
        Mockito.when(tokenService.validateToken(TOKEN, Claim.UPDATE_PROFILE)).thenReturn("Success"); 
 
        //When 
        ResponseEntity<CommonResponse> response = customerController.customerProfileUpdate(CUSTOMER_ID, TOKEN, updateCustomerProfileRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response.getBody()); 
    } 
 
    @Test 
    public void testGetCustomerDashboard_Empty() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.EMPTY_DASHBOARD); 
        Mockito.when(bookApplicationService.getCustomerDashboard(1L)).thenReturn(null); 
 
        //When 
        ResponseEntity<DashboardResponseDto> response = customerController.getCustomerDashboard(1L); 
 
        //Then 
        validateAssertions(commonResponse, response.getBody().getCommonResponse()); 
    } 
 
    @Test 
    public void testGetCustomerDashboard() { 
        //Given 
        commonResponse.setMessage(ApplicationConstants.EMPTY_STRING); 
        Mockito.when(bookApplicationService.getCustomerDashboard(1L)).thenReturn(dashboardResponseDto); 
 
        //When 
        ResponseEntity<DashboardResponseDto> response = customerController.getCustomerDashboard(1L); 
 
        //Then 
        Assertions.assertNotNull(response); 
        Assertions.assertEquals(1, response.getBody().getBooksLent().size()); 
        Assertions.assertEquals(1, response.getBody().getBooksRent().size()); 
        validateAssertions(commonResponse, response.getBody().getCommonResponse()); 
    } 
 
    @Test 
    public void testSaveFeedback() { 
        //Given 
        commonResponse.setMessage(ApplicationConstants.FEEDBACK_SAVE_SUCCESSFULLY); 
        Mockito.doNothing().when(feedbackService).saveFeedback(feedbackRequestDto); 
 
        //When 
        ResponseEntity<CommonResponse> response = customerController.saveFeedback(feedbackRequestDto); 
 
        //Then 
        validateAssertions(commonResponse, response.getBody()); 
    } 
 
    @Test 
    public void testGetCustomerDetail() { 
        //Given 
        Mockito.when(customerService.isCustomerPresent(1L)).thenReturn(customer); 
 
        //When 
        ResponseEntity<GetCustomerResponseDto> response = customerController.getCustomerDetails(1L); 
 
        //Then 
        Assertions.assertEquals(customer.getCustomerId(), response.getBody().getCustomerId()); 
        Assertions.assertEquals(customer.getCustomerName(), response.getBody().getCustomerName()); 
        Assertions.assertEquals(customer.getMobileNumber(), response.getBody().getMobileNumber()); 
    } 
 
    private void validateAssertions(CommonResponse commonResponse, CommonResponse response) { 
        Assertions.assertEquals(commonResponse.getMessage(), response.getMessage()); 
        Assertions.assertEquals(commonResponse.getResult(), response.getResult()); 
    } 
 
} 