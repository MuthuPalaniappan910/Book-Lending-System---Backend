package com.booklending.book.service;

import com.booklending.book.dto.BookDamageDto; 
import com.booklending.book.dto.NewBookDamageRequestDto; 
import com.booklending.book.entity.Book; 
 
import java.util.List; 
 
public interface BookDamageService {
	
    List<BookDamageDto> getBookDamageList(Book book); 
 
    void saveDetailsOfBookDamage(List<NewBookDamageRequestDto> newBookDamageRequestDtoList, Book book); 
 
    void deleteBookDamages(Book book); 
} 

