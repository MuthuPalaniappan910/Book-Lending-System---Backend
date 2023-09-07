package com.booklending.customer.service;

import com.booklending.book.entity.Category; 
import com.booklending.book.repository.CategoryRepository; 
import org.junit.jupiter.api.Assertions; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.Mockito; 
import org.mockito.junit.jupiter.MockitoExtension; 
 
@ExtendWith(MockitoExtension.class) 
public class CategoryServiceTest { 
 
    @InjectMocks 
    private CategoryServiceImpl categoryService; 
 
    @Mock 
    private CategoryRepository categoryRepository; 
 
    private Category category; 
 
    private final static String CATEGORY = "Comic"; 
 
    @BeforeEach 
    public void before() { 
        category = new Category(); 
        category.setCategory(CATEGORY); 
        category.setCategoryId(1L); 
    } 
 
    @Test 
    public void testGetCategoryDetails() { 
        //Given 
        Mockito.when(categoryRepository.findByCategory(CATEGORY)).thenReturn(category); 
 
        //When 
        Category response = categoryService.getCategoryDetails(CATEGORY); 
 
        //Then 
        Assertions.assertNotNull(response); 
        Assertions.assertEquals(category.getCategoryId(), response.getCategoryId()); 
    } 
} 
s
