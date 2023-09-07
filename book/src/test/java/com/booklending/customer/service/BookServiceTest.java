package com.booklending.customer.service;

import com.booklending.book.dto.BookDamageDto; 
import com.booklending.book.dto.BookDetailsDto; 
import com.booklending.book.dto.BookDto; 
import com.booklending.book.dto.CommonResponse; 
import com.booklending.book.dto.NewBookDamageRequestDto; 
import com.booklending.book.dto.NewBookRequestDto; 
import com.booklending.book.dto.NewBookResponseDto; 
import com.booklending.book.entity.Book; 
import com.booklending.book.entity.Category; 
import com.booklending.book.repository.BookRepository; 
import com.booklending.book.utils.ApplicationConstants; 
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
import org.springframework.beans.BeanUtils; 
 
import java.util.ArrayList; 
import java.util.List; 
 
@ExtendWith(MockitoExtension.class) 
@MockitoSettings(strictness = Strictness.LENIENT) 
public class BookServiceTest { 
 
    @InjectMocks 
    private BookServiceImpl bookService; 
 
    @Mock 
    private CategoryService categoryService; 
 
    @Mock 
    private BookDamageService bookDamageService; 
 
    @Mock 
    private BookRepository bookRepository; 
 
    @Mock 
    private LendService lendService; 
 
    private final static String AUTHOR = "MSD"; 
    private final static String CATEGORY = "Comic"; 
    private final static String TITLE = "ABC"; 
 
    private Category category; 
    private Book book; 
    private Book newBook; 
    private List<Book> bookList; 
    private List<Book> bookListEmpty = new ArrayList<>(); 
    private NewBookResponseDto newBookResponseDto; 
    private List<NewBookRequestDto> newBookRequestDtos; 
    private NewBookRequestDto newBookRequestDto; 
    private List<NewBookDamageRequestDto> newBookDamageRequestDtoList; 
    private NewBookDamageRequestDto newBookDamageRequestDto; 
    private CommonResponse commonResponse; 
    private List<BookDamageDto> bookDamageDtoList_empty = new ArrayList<>(); 
    private List<BookDamageDto> bookDamageDtoList; 
    BookDamageDto bookDamageDto; 
 
    @BeforeEach 
    public void before() { 
        commonResponse = new CommonResponse(); 
 
        category = new Category(); 
        category.setCategory(CATEGORY); 
 
        book = new Book(); 
        book.setBookId(1L); 
        book.setTitle("Title"); 
        book.setDescription("Desc"); 
        book.setAuthor("Auth"); 
        book.setCategory(category); 
        book.setPerDayPrice(2.5d); 
        book.setTotalStock(1); 
        book.setRented(0); 
 
        bookList = new ArrayList<>(); 
        bookList.add(book); 
 
        newBookDamageRequestDto = new NewBookDamageRequestDto(); 
        newBookDamageRequestDto.setDamageDescription("Page 17 torn"); 
 
        newBookDamageRequestDtoList = new ArrayList<>(); 
        newBookDamageRequestDtoList.add(newBookDamageRequestDto); 
 
        newBookRequestDto = new NewBookRequestDto(); 
        newBookRequestDto.setAuthor("MSD"); 
        newBookRequestDto.setCategory(CATEGORY); 
        newBookRequestDto.setTitle("Captain"); 
        newBookRequestDto.setDescription("Cricbuzz"); 
        newBookRequestDto.setMrp(200d); 
        newBookRequestDto.setCustomerId(7L); 
        newBookRequestDto.setTotalStock(1); 
        newBookRequestDto.setNewBookDamageRequestDtoList(newBookDamageRequestDtoList); 
 
        newBookRequestDtos = new ArrayList<>(); 
        newBookRequestDtos.add(newBookRequestDto); 
 
        newBookResponseDto = new NewBookResponseDto(); 
 
        newBook = new Book(); 
        BeanUtils.copyProperties(newBookRequestDto, newBook); 
 
        bookDamageDto = new BookDamageDto(); 
        bookDamageDto.setDamageDescription("Page 17 torn"); 
 
        bookDamageDtoList = new ArrayList<>(); 
        bookDamageDtoList.add(bookDamageDto); 
    } 
 
    @Test 
    public void testGetBooksByTitle() { 
        //Given 
        Mockito.when(bookRepository.findAllByTitle(TITLE)).thenReturn(bookListEmpty); 
 
        //When 
        List<BookDto> response = bookService.getBooksByTitle(TITLE); 
 
        //Then 
        Assertions.assertEquals(0, response.size()); 
        Mockito.verify(bookRepository, Mockito.times(1)).findAllByTitle(TITLE); 
    } 
 
    @Test 
    public void testGetBooksByAuthor() { 
        //Given 
        Mockito.when(bookRepository.findAllByAuthor(AUTHOR)).thenReturn(bookListEmpty); 
 
        //When 
        List<BookDto> response = bookService.getBooksByAuthor(AUTHOR); 
 
        //Then 
        Assertions.assertEquals(0, response.size()); 
        Mockito.verify(bookRepository, Mockito.times(1)).findAllByAuthor(AUTHOR); 
    } 
 
    @Test 
    public void testGetBooksByCategory() { 
        //Given 
        Mockito.when(categoryService.getCategoryDetails(CATEGORY)).thenReturn(category); 
        Mockito.when(bookRepository.findAllByCategory(category)).thenReturn(bookList); 
 
        //When 
        List<BookDto> response = bookService.getBooksByCategory(CATEGORY); 
 
        //Then 
        Assertions.assertNotNull(response); 
        Assertions.assertEquals(1, response.size()); 
        Mockito.verify(categoryService, Mockito.times(1)).getCategoryDetails(CATEGORY); 
        Mockito.verify(bookRepository, Mockito.times(1)).findAllByCategory(category); 
    } 
 
    @Test 
    public void testAddNewBook() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.EMPTY_STRING); 
        newBookResponseDto.setResponse(commonResponse); 
        Mockito.when(categoryService.getCategoryDetails(CATEGORY)).thenReturn(category); 
        Mockito.when(bookRepository.save(newBook)).thenReturn(newBook); 
        Mockito.doNothing().when(bookDamageService).saveDetailsOfBookDamage( 
                newBookRequestDto.getNewBookDamageRequestDtoList(), newBook); 
 
        //When 
        NewBookResponseDto response = bookService.addNewBook(newBookRequestDtos); 
 
        //Then 
        Assertions.assertNotNull(response); 
        validateCommonResponse(commonResponse, response.getResponse()); 
    } 
 
    @Test 
    public void testDeleteBook_NotFound() { 
        //Given 
        commonResponse.setResult(Boolean.FALSE); 
        commonResponse.setMessage(ApplicationConstants.INVALID_BOOK_ID); 
        newBookResponseDto.setResponse(commonResponse); 
        Mockito.when(bookRepository.findByBookId(1L)).thenReturn(null); 
 
        //When 
        CommonResponse response = bookService.deleteBook(1L); 
 
        //Then 
        validateCommonResponse(commonResponse, response); 
    } 
 
    @Test 
    public void testDeleteBook() { 
        //Given 
        commonResponse.setResult(Boolean.TRUE); 
        commonResponse.setMessage(ApplicationConstants.BOOK_DELETED_SUCCESSFULLY); 
        Mockito.when(bookRepository.findByBookId(1L)).thenReturn(book); 
        Mockito.doNothing().when(bookDamageService).deleteBookDamages(book); 
        Mockito.doNothing().when(lendService).deleteBookLend(book); 
        Mockito.doNothing().when(bookRepository).deleteByBookId(1L); 
 
        //When 
        CommonResponse response = bookService.deleteBook(1L); 
 
        //Then 
        validateCommonResponse(commonResponse, response); 
    } 
 
    @Test 
    public void testGetBookDetails() { 
        //Given 
        Mockito.when(bookRepository.findByBookId(1L)).thenReturn(book); 
 
        //When 
        String response = bookService.getBookDetails(1L); 
 
        //Then 
        Assertions.assertEquals(book.getBookName(), response); 
    } 
 
    @Test 
    public void testGetBookAndDamageDetails_null() { 
        //Given 
        Mockito.when(bookRepository.findByBookId(1L)).thenReturn(new Book()); 
 
        //When 
        BookDetailsDto response = bookService.getBookAndDamageDetails(1L); 
 
        //Then 
        Assertions.assertNull(response.getBookDto()); 
    } 
 
    @Test 
    public void testGetBookAndDamageDetails_WithoutDamage() { 
        //Given 
        Mockito.when(bookRepository.findByBookId(1L)).thenReturn(book); 
        Mockito.when(bookDamageService.getBookDamageList(book)).thenReturn(bookDamageDtoList_empty); 
 
        //When 
        BookDetailsDto response = bookService.getBookAndDamageDetails(1L); 
 
        //Then 
        Assertions.assertNotNull(response.getBookDto()); 
        Assertions.assertNotNull(response.getBookDto()); 
        Assertions.assertEquals(Boolean.FALSE, response.getBookDamaged()); 
    } 
 
    @Test 
    public void testGetBookAndDamageDetails_WithDamage() { 
        //Given 
        Mockito.when(bookRepository.findByBookId(1L)).thenReturn(book); 
        Mockito.when(bookDamageService.getBookDamageList(book)).thenReturn(bookDamageDtoList); 
 
        //When 
        BookDetailsDto response = bookService.getBookAndDamageDetails(1L); 
 
        //Then 
        Assertions.assertNotNull(response.getBookDto()); 
        Assertions.assertNotNull(response.getBookDto()); 
        Assertions.assertEquals(Boolean.TRUE, response.getBookDamaged()); 
        Assertions.assertEquals(1, response.getBookDamageDtoList().size()); 
    } 
 
    @Test 
    public void testUpdateRented() { 
        //Given 
        Mockito.when(bookRepository.findByBookId(1L)).thenReturn(book); 
 
        //When 
        Book response = bookService.updateRented(1L); 
 
        //Then 
        Assertions.assertNotNull(response); 
        Assertions.assertEquals(1, response.getRented()); 
    } 
 
    private void validateCommonResponse(CommonResponse commonResponse, CommonResponse response) { 
        Assertions.assertEquals(commonResponse.getMessage(), response.getMessage()); 
        Assertions.assertEquals(commonResponse.getResult(), response.getResult()); 
    } 
} 

