package com.booklending.book.repository;

import com.booklending.book.entity.Book; 
import com.booklending.book.entity.Lend; 
import org.springframework.data.jpa.repository.JpaRepository; 
 
import java.time.LocalDate; 
import java.util.List; 
 
public interface LendRepository extends JpaRepository<Lend, Long> { 
	
    List<Lend> findAllByFromCustomerId(Long customerId); 
 
    List<Lend> findAllByToCustomerId(Long customerId); 
 
    Lend findByLendId(Long lendId); 
    
    List<Lend> findByEndDate(LocalDate minusDays); 
 
    void deleteAllByBook(Book book); 
} 
