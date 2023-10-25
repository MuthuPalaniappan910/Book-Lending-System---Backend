package com.booklending.customer.controller;

import com.booklending.book.dto.BookDetailsDto; 
import com.booklending.book.dto.BookDto; 
import com.booklending.book.dto.PurchaseRequestDto; 
import com.booklending.book.dto.CommonResponse; 
import com.booklending.book.dto.DashboardResponseDto; 
import com.booklending.book.dto.NewBookRequestDto; 
import com.booklending.book.dto.NewBookResponseDto; 
import com.booklending.book.dto.SearchBookRequestDto; 
import com.booklending.book.dto.SearchBookResponseDto; 
import com.booklending.book.service.BookService; 
import com.booklending.book.service.LendService; 
import com.booklending.book.utils.ApplicationConstants; 
import org.junit.jupiter.api.Assertions; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.Mockito; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.HttpStatusCode; 
import org.springframework.http.ResponseEntity; 
 
import java.time.LocalDate; 
import java.util.ArrayList; 
import java.util.List; 
 
@ExtendWith(MockitoExtension.class) 
public class BookControllerTest { 
 
    @InjectMocks 
    private BookController bookController; 
 
    @Mock 
    private BookService bookService; 
 
    @Mock 
    private LendService lendService; 
 
    private final static String AUTHOR = "MSD"; 
    private final static String CATEGORY = "Comic"; 
    private final static String TITLE = "ABC"; 
 
    SearchBookRequestDto searchBookRequestDto; 
    CommonResponse commonResponse; 
    private List<BookDto> bookDtoEmpty = new ArrayList<>(); 
    private List<NewBookRequestDto> newBookRequestDtosEmpty = new ArrayList<>(); 
    private List<BookDto> bookDtos; 
    private List<NewBookRequestDto> newBookRequestDtos; 
    NewBookRequestDto newBookRequestDto; 
    NewBookResponseDto newBookResponseDto; 
    BookDto bookDtoCategory; 
    BookDto bookDtoTitle; 
    BookDto bookDtoAuthor; 
    BookDetailsDto bookDetailsDto; 
    PurchaseRequestDto purchaseRequestDto; 
 
    @BeforeEach 
    public void before() { 
        searchBookRequestDto = new SearchBookRequestDto(); 
        searchBookRequestDto.setAuthor(AUTHOR); 
        searchBookRequestDto.setCategory(CATEGORY); 
        searchBookRequestDto.setTitle(TITLE); 
 
        commonResponse = new CommonResponse(); 
 
        bookDtoCategory = new BookDto(); 
        bookDtoCategory.setBookId(1L); 
        bookDtoCategory.setCategory(CATEGORY); 
 
        bookDtoTitle = new BookDto(); 
        bookDtoTitle.setBookId(1L); 
        bookDtoTitle.setTitle(TITLE); 
 
        bookDtoAuthor = new BookDto(); 
        bookDtoAuthor.setBookId(1L); 
        bookDtoAuthor.setAuthor(AUTHOR); 
 
        bookDtos = new ArrayList<>(); 
        bookDtos.add(bookDtoCategory); 
        bookDtos.add(bookDtoTitle); 
        bookDtos.add(bookDtoAuthor); 
 
        newBookRequestDto = new NewBookRequestDto(); 
        newBookRequestDto.setAuthor("MSD"); 
        newBookRequestDto.setCategory("Cricket"); 
        newBookRequestDto.setTitle("Captain"); 
        newBookRequestDto.setDescription("Cricbuzz"); 
        newBookRequestDto.setMrp(200d); 
        newBookRequestDto.setCustomerId(7L); 
        newBookRequestDto.setTotalStock(1); 
 
        newBookRequestDtos = new ArrayList<>(); 
        newBookRequestDtos.add(newBookRequestDto); 
 
        newBookResponseDto = new NewBookResponseDto(); 
 
        bookDetailsDto = new BookDetailsDto(); 
        bookDetailsDto.setBookDto(bookDtoTitle); 
        bookDetailsDto.setBookDamaged(Boolean.FALSE); 
 
        purchaseRequestDto = new PurchaseRequestDto(); 
        purchaseRequestDto.setBookId(1L); 
        purchaseRequestDto.setCustomerId(1L); 
        purchaseRequestDto.setNumberOfDays(15); 
    } 
 
    @Test 
    public void testSearchBooks_Empty() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.NO_BOOKS_FOR_SEARCH_CRITERIA); 
        Mockito.when(bookService.getBooksByTitle(searchBookRequestDto.getTitle())).thenReturn(bookDtoEmpty); 
        Mockito.when(bookService.getBooksByAuthor(searchBookRequestDto.getAuthor())).thenReturn(bookDtoEmpty); 
        Mockito.when(bookService.getBooksByCategory(searchBookRequestDto.getCategory())).thenReturn(bookDtoEmpty); 
 
        //When 
        ResponseEntity<SearchBookResponseDto> response = bookController.searchBooks(searchBookRequestDto); 
 
        //Then 
        validateHttpStatus(response.getStatusCode()); 
        validateCommonResponse(commonResponse, response.getBody().getCommonResponse()); 
        validateVerifyForSearchBooks(); 
        Assertions.assertNull(response.getBody().getBooks()); 
    } 
 
 
    @Test 
    public void testSearchBooks() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.EMPTY_STRING); 
        Mockito.when(bookService.getBooksByTitle(searchBookRequestDto.getTitle())).thenReturn(bookDtos); 
        Mockito.when(bookService.getBooksByAuthor(searchBookRequestDto.getAuthor())).thenReturn(bookDtos); 
        Mockito.when(bookService.getBooksByCategory(searchBookRequestDto.getCategory())).thenReturn(bookDtos); 
 
        //When 
        ResponseEntity<SearchBookResponseDto> response = bookController.searchBooks(searchBookRequestDto); 
 
        //Then 
        validateHttpStatus(response.getStatusCode()); 
        validateCommonResponse(commonResponse, response.getBody().getCommonResponse()); 
        validateVerifyForSearchBooks(); 
        Assertions.assertNotNull(response.getBody().getBooks()); 
        Assertions.assertEquals(3, response.getBody().getBooks().size()); 
    } 
 
    @Test 
    public void testAddBook_Empty() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.NO_BOOKS_ADDED_FOR_LEND); 
 
        //When 
        ResponseEntity<NewBookResponseDto> response = bookController.addNewBook(newBookRequestDtosEmpty); 
 
        //Then 
        validateHttpStatus(response.getStatusCode()); 
        validateCommonResponse(commonResponse, response.getBody().getResponse()); 
    } 
 
    @Test 
    public void testAddBook() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.EMPTY_STRING); 
        newBookResponseDto.setResponse(commonResponse); 
        Mockito.when(bookService.addNewBook(newBookRequestDtos)).thenReturn(newBookResponseDto); 
 
        //When 
        ResponseEntity<NewBookResponseDto> response = bookController.addNewBook(newBookRequestDtos); 
 
        //Then 
        validateHttpStatus(response.getStatusCode()); 
        validateCommonResponse(commonResponse, response.getBody().getResponse()); 
    } 
 
    @Test 
    public void testGetCustomerDashboard_Null() { 
        //Given 
 
        Mockito.when(lendService.getCustomerDashboard(1L)).thenReturn(null); 
 
        //When 
        ResponseEntity<DashboardResponseDto> response = bookController.getCustomerDashboard(1L); 
 
        //Then 
        Assertions.assertNull(response.getBody()); 
    } 
 
    @Test 
    public void testDeleteBook() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.BOOK_DELETED_SUCCESSFULLY); 
        Mockito.when(bookService.deleteBook(1L)).thenReturn(commonResponse); 
 
        //When 
        ResponseEntity<CommonResponse> response = bookController.deleteBook(1L); 
 
        //Then 
        validateHttpStatus(response.getStatusCode()); 
        validateCommonResponse(commonResponse, response.getBody()); 
    } 
 
    @Test 
    public void testGetBookAndDamageDetails_Null() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.INVALID_BOOK_ID); 
        Mockito.when(bookService.getBookAndDamageDetails(1L, 1L)).thenReturn(new BookDetailsDto()); 
 
        //When 
        ResponseEntity<BookDetailsDto> response = bookController.getBookAndDamageDetails(1L, 1L); 
 
        //Then 
        Assertions.assertNotNull(response.getBody()); 
        validateHttpStatus(response.getStatusCode()); 
        validateCommonResponse(commonResponse, response.getBody().getCommonResponse()); 
    } 
 
    @Test 
    public void testGetBookAndDamageDetails() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.EMPTY_STRING); 
        Mockito.when(bookService.getBookAndDamageDetails(1L)).thenReturn(bookDetailsDto); 
 
        //When 
        ResponseEntity<BookDetailsDto> response = bookController.getBookAndDamageDetails(1L); 
 
        //Then 
        Assertions.assertNotNull(response.getBody()); 
        Assertions.assertNotNull(response.getBody().getBookDto()); 
        Assertions.assertEquals(Boolean.FALSE, response.getBody().getBookDamaged()); 
        validateHttpStatus(response.getStatusCode()); 
        validateCommonResponse(commonResponse, response.getBody().getCommonResponse()); 
    } 
 
    @Test 
    public void testAddCart() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.ORDER_SUCCESSFUL); 
        Mockito.when(lendService.addCart(purchaseRequestDto)).thenReturn(Mockito.any()); 
 
        //When 
        ResponseEntity<CommonResponse> response = bookController.addCart(purchaseRequestDto); 
 
        //Then 
        Assertions.assertNotNull(response); 
    } 
 
    @Test 
    public void testRenewBook() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.RENEW_SUCCESSFUL + 
                LocalDate.now().plusDays(ApplicationConstants.RENEW_DAYS)); 
        Mockito.when(lendService.renewBook(1L)) 
                .thenReturn(LocalDate.now().plusDays(ApplicationConstants.RENEW_DAYS)); 
 
        //When 
        ResponseEntity<CommonResponse> response = bookController.renewBook(1L); 
 
        //Then 
        validateHttpStatus(response.getStatusCode()); 
        validateCommonResponse(commonResponse, response.getBody()); 
    } 
 
    private void validateHttpStatus(HttpStatusCode response) { 
        Assertions.assertEquals(200, response.value()); 
    } 
 
    private void validateVerifyForSearchBooks() { 
        Mockito.verify(bookService, Mockito.times(1)).getBooksByTitle(searchBookRequestDto.getTitle()); 
        Mockito.verify(bookService, Mockito.times(1)).getBooksByAuthor(searchBookRequestDto.getAuthor()); 
        Mockito.verify(bookService, Mockito.times(1)).getBooksByCategory(searchBookRequestDto.getCategory()); 
    } 
 
    private void validateCommonResponse(CommonResponse commonResponse, CommonResponse response) { 
        Assertions.assertEquals(commonResponse.getMessage(), response.getMessage()); 
        Assertions.assertEquals(commonResponse.getResult(), response.getResult()); 
    } 
} 
