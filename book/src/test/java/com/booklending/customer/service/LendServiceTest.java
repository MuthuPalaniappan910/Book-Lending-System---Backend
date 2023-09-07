package com.booklending.customer.service;

import com.booklending.book.dto.PurchaseRequestDto; 
import com.booklending.book.dto.DashboardResponseDto; 
import com.booklending.book.entity.Book; 
import com.booklending.book.entity.Lend; 
import com.booklending.book.repository.LendRepository; 
import org.junit.jupiter.api.Assertions; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.Mockito; 
import org.mockito.junit.jupiter.MockitoExtension; 
 
import java.time.LocalDate; 
import java.util.ArrayList; 
import java.util.List; 
 
@ExtendWith(MockitoExtension.class) 
public class LendServiceTest { 
 
    @InjectMocks 
    private LendServiceImpl lendService; 
 
    @Mock 
    private LendRepository lendRepository; 
 
    @Mock 
    private BookService bookService; 
 
    List<Lend> lendList; 
    List<Lend> rentList; 
    Lend lend; 
    Lend rent; 
    Book book; 
    PurchaseRequestDto purchaseRequestDto; 
 
    @BeforeEach 
    public void before() { 
        book = new Book(); 
        book.setBookName("ABC"); 
        book.setBookId(1L); 
        book.setPerDayPrice(1.25d); 
 
        lend = new Lend(); 
        lend.setLendId(1L); 
        lend.setBook(book); 
        lend.setEndDate(LocalDate.now()); 
        lend.setStartDate(LocalDate.now().minusDays(2)); 
        lend.setFromCustomerId(1L); 
 
        rent = new Lend(); 
        rent.setLendId(1L); 
        rent.setBook(book); 
        rent.setEndDate(LocalDate.now().minusDays(1)); 
        rent.setStartDate(LocalDate.now().minusDays(4)); 
        rent.setToCustomerId(1L); 
 
        lendList = new ArrayList<>(); 
        lendList.add(lend); 
 
        rentList = new ArrayList<>(); 
        rentList.add(rent); 
 
        purchaseRequestDto = new PurchaseRequestDto(); 
        purchaseRequestDto.setBookId(1L); 
        purchaseRequestDto.setCustomerId(1L); 
        purchaseRequestDto.setNumberOfDays(15); 
    } 
 
    @Test 
    public void testGetCustomerDashboard_Null() { 
        //Given 
        Mockito.when(lendRepository.findAllByFromCustomerId(1L)).thenReturn(new ArrayList<>()); 
        Mockito.when(lendRepository.findAllByToCustomerId(1L)).thenReturn(new ArrayList<>()); 
 
        //When 
        DashboardResponseDto response = lendService.getCustomerDashboard(1L); 
 
        //Then 
        Assertions.assertNull(response); 
    } 
 
    @Test 
    public void testGetCustomerDashboard_NotRenewable() { 
        //Given 
        Mockito.when(lendRepository.findAllByFromCustomerId(1L)).thenReturn(lendList); 
        Mockito.when(lendRepository.findAllByToCustomerId(1L)).thenReturn(rentList); 
        Mockito.when(bookService.getBookDetails(1L)).thenReturn(book.getBookName()); 
 
        //When 
        DashboardResponseDto response = lendService.getCustomerDashboard(1L); 
 
        //Then 
        Assertions.assertNotNull(response); 
        Assertions.assertEquals(1, response.getBooksRent().size()); 
        Assertions.assertEquals(1, response.getBooksLent().size()); 
    } 
 
    @Test 
    public void testGetCustomerDashboard_Renewable() { 
        //Given 
        rent.setEndDate(LocalDate.now().plusDays(3)); 
        rentList.add(rent); 
        Mockito.when(lendRepository.findAllByFromCustomerId(1L)).thenReturn(lendList); 
        Mockito.when(lendRepository.findAllByToCustomerId(1L)).thenReturn(rentList); 
        Mockito.when(bookService.getBookDetails(1L)).thenReturn(book.getBookName()); 
 
        //When 
        DashboardResponseDto response = lendService.getCustomerDashboard(1L); 
 
        //Then 
        Assertions.assertNotNull(response); 
        Assertions.assertEquals(2, response.getBooksRent().size()); 
        Assertions.assertEquals(1, response.getBooksLent().size()); 
    } 
 
    @Test 
    public void testAddCart() { 
        //Given 
        Mockito.when(bookService.updateRented(purchaseRequestDto.getBookId())).thenReturn(book); 
        lendRepository.save(lend); 
 
        //When 
        String message = lendService.addCart(purchaseRequestDto); 
 
        //Then 
        Assertions.assertNotNull(message); 
    } 
 
    @Test 
    public void testRenewBook() { 
        //Given 
        rent.setEndDate(LocalDate.now().plusDays(3)); 
        Mockito.when(lendRepository.findByLendId(1L)).thenReturn(rent); 
        lendRepository.save(rent); 
 
        //When 
        LocalDate response = lendService.renewBook(1L); 
 
        //Then 
        Assertions.assertEquals(rent.getEndDate(),  response); 
    } 
} 

