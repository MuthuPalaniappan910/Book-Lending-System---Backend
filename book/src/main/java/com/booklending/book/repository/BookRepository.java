package com.booklending.book.repository;

import com.booklending.book.entity.Book; 
import com.booklending.book.entity.Category; 
import org.springframework.data.jpa.repository.JpaRepository; 
 
import java.util.List; 

public interface BookRepository extends JpaRepository<Book, Long> { 
 
    Book findByBookId(Long bookId); 
 
    List<Book> findAllByCategory(Category category); 
 
    List<Book> findAllByTitle(String title); 
 
    List<Book> findAllByAuthor(String author); 
 
    void deleteByBookId(Long bookId); 
 
} 

