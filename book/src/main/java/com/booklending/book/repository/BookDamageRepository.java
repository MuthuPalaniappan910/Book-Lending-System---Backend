package com.booklending.book.repository;

import com.booklending.book.entity.Book; 
import com.booklending.book.entity.BookDamage; 
import org.springframework.data.jpa.repository.JpaRepository; 
 
import java.util.List; 
 
public interface BookDamageRepository extends JpaRepository<BookDamage, Long> { 
    List<BookDamage> findAllByBook(Book book); 
 
    void deleteAllByBook(Book book); 
} 
