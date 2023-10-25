package com.booklending.book.service;

import com.booklending.book.dto.BookDetailsDto; 
import com.booklending.book.dto.BookDto; 
import com.booklending.book.dto.CommonResponse; 
import com.booklending.book.dto.NewBookRequestDto; 
import com.booklending.book.dto.NewBookResponseDto; 
import com.booklending.book.entity.Book; 
 
import java.util.List; 
 
public interface BookService { 
 
    CommonResponse deleteBook(Long bookId, Long customerId); 
 
    NewBookResponseDto addNewBook(List<NewBookRequestDto> newBookRequestDtos); 
 
    List<BookDto> getBooksByTitle(String title); 
 
    List<BookDto> getBooksByCategory(String category); 
 
    List<BookDto> getBooksByAuthor(String author); 
 
    String getBookDetails(Long bookId); 
 
    BookDetailsDto getBookAndDamageDetails(Long bookId); 
 
    Book updateRented(Long bookId); 
} 
