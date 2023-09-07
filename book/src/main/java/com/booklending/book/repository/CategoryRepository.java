package com.booklending.book.repository;

import com.booklending.book.entity.Category; 
import org.springframework.data.jpa.repository.JpaRepository; 
 
public interface CategoryRepository extends JpaRepository<Category, Long> { 
 
    Category findByCategory(String category); 
} 
