package com.booklending.book.service;

import com.booklending.book.entity.Category; 
import com.booklending.book.repository.CategoryRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
@Service 
public class CategoryServiceImpl implements CategoryService { 
 
    @Autowired 
    private CategoryRepository categoryRepository; 
 
    @Override 
    public Category getCategoryDetails(String category) { 
        return categoryRepository.findByCategory(category); 
    } 
} 

