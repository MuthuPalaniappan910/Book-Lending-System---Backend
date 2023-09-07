package com.booklending.customer.service;

import com.booklending.book.dto.BookDamageDto; 
import com.booklending.book.dto.NewBookDamageRequestDto; 
import com.booklending.book.dto.NewBookRequestDto; 
import com.booklending.book.entity.Book; 
import com.booklending.book.entity.BookDamage; 
import com.booklending.book.repository.BookDamageRepository; 
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
 
import java.util.ArrayList; 
import java.util.List; 
 
@ExtendWith(MockitoExtension.class) 
@MockitoSettings(strictness = Strictness.LENIENT) 
public class BookDamageServiceTest { 
 
    @InjectMocks
    private BookDamageServiceImpl bookDamageService; 
 
    @Mock 
    private BookDamageRepository bookDamageRepository; 
 
    private List<BookDamage> bookDamageList; 
    private List<BookDamage> bookDamageListEmpty = new ArrayList<>(); 
    private List<NewBookDamageRequestDto> newBookDamageRequestDtoList; 
    private NewBookDamageRequestDto newBookDamageRequestDto; 
    private List<NewBookRequestDto> newBookRequestDtos; 
    private NewBookRequestDto newBookRequestDto; 
    private Book book; 
    private BookDamage bookDamage; 
 
    @BeforeEach 
    public void before() { 
        book = new Book(); 
        book.setBookId(1L); 
 
        bookDamage = new BookDamage(); 
        bookDamage.setBook(book); 
 
        bookDamageList = new ArrayList<>(); 
        bookDamageList.add(bookDamage); 
 
        newBookDamageRequestDto = new NewBookDamageRequestDto(); 
        newBookDamageRequestDto.setDamageDescription("Page 17 torn"); 
 
        newBookDamageRequestDtoList = new ArrayList<>(); 
        newBookDamageRequestDtoList.add(newBookDamageRequestDto); 
 
        newBookRequestDto = new NewBookRequestDto(); 
        newBookRequestDto.setAuthor("MSD"); 
        newBookRequestDto.setCategory("Comic"); 
        newBookRequestDto.setTitle("Captain"); 
        newBookRequestDto.setDescription("Cricbuzz"); 
        newBookRequestDto.setMrp(200d); 
        newBookRequestDto.setCustomerId(7L); 
        newBookRequestDto.setTotalStock(1); 
        newBookRequestDto.setNewBookDamageRequestDtoList(newBookDamageRequestDtoList); 
 
        newBookRequestDtos = new ArrayList<>(); 
        newBookRequestDtos.add(newBookRequestDto); 
 
    } 
 
    @Test 
    public void testSaveDetailsOfBookDamages() { 
        //Given 
        Mockito.when(bookDamageRepository.save(bookDamage)).thenReturn(bookDamage); 
 
        //When 
        bookDamageService.saveDetailsOfBookDamage(newBookRequestDto.getNewBookDamageRequestDtoList(), book); 
 
        //Then 
        Mockito.verify(bookDamageRepository, Mockito.times(1)).save(Mockito.any()); 
    } 
 
    @Test 
    public void testDeleteBookDamages() { 
        //Given 
        Mockito.doNothing().when(bookDamageRepository).deleteAllByBook(book); 
 
        //When 
        bookDamageService.deleteBookDamages(book); 
 
        //Then 
        Mockito.verify(bookDamageRepository, Mockito.times(1)).deleteAllByBook(book); 
    } 
 
    @Test 
    public void testGetBookDamages_Empty() { 
        //Given 
        Mockito.when(bookDamageRepository.findAllByBook(book)).thenReturn(bookDamageListEmpty); 
 
        //When 
        List<BookDamageDto> response = bookDamageService.getBookDamageList(book); 
 
        //Then 
        Assertions.assertEquals(0, response.size()); 
    } 
 
    @Test 
    public void testGetBookDamages() { 
        //Given 
        Mockito.when(bookDamageRepository.findAllByBook(book)).thenReturn(bookDamageList); 
 
        //When 
        List<BookDamageDto> response = bookDamageService.getBookDamageList(book); 
 
        //Then 
        Assertions.assertEquals(1, response.size()); 
    } 


}
