package com.booklending.book.service;

import com.booklending.book.dto.BookDamageDto; 
import com.booklending.book.dto.NewBookDamageRequestDto; 
import com.booklending.book.entity.Book; 
import com.booklending.book.entity.BookDamage; 
import com.booklending.book.repository.BookDamageRepository; 
import org.springframework.beans.BeanUtils; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
import java.util.ArrayList; 
import java.util.List; 
 
@Service 
public class BookDamageServiceImpl implements BookDamageService{ 
 
    @Autowired 
    private BookDamageRepository bookDamageRepository; 
 
    @Override 
    public List<BookDamageDto> getBookDamageList(Book book) { 
        List<BookDamageDto> bookDamageDtoList = new ArrayList<>(); 
        List<BookDamage> bookDamages = bookDamageRepository.findAllByBook(book); 
        if (bookDamages.isEmpty()) { 
            return bookDamageDtoList; 
        } 
        bookDamages.forEach ( bookDamage -> { 
            BookDamageDto bookDamageDto = new BookDamageDto(); 
            BeanUtils.copyProperties(bookDamage, bookDamageDto); 
            bookDamageDtoList.add(bookDamageDto); 
        }); 
        return bookDamageDtoList; 
    } 
 
    @Override 
    public void saveDetailsOfBookDamage(List<NewBookDamageRequestDto> newBookDamageRequestDtoList, Book book) { 
        newBookDamageRequestDtoList.forEach (bookDamageDetails -> { 
            BookDamage bookDamage = new BookDamage(); 
            bookDamage.setDamageDescription(bookDamageDetails.getDamageDescription()); 
            bookDamage.setBook(book); 
            bookDamageRepository.saveAndFlush(bookDamage); 
        }); 
 
    } 
 
    @Override 
    public void deleteBookDamages(Book book) { 
        bookDamageRepository.deleteAllByBook(book); 
    } 
} 
