package com.booklending.book.entity;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Index; 
import jakarta.persistence.Table; 
 
@Entity 
@Table(name = "Category", indexes = @Index (name = "category_index", columnList = "category")) 
public class Category { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column (name = "CATEGORY_ID") 
    private Long categoryId; 
 
    @Column (name = "CATEGORY", nullable = false) 
    private String category; 
 
    @Column (name = "DESCRIPTION", nullable = false) 
    private String description; 
 
    public Long getCategoryId() { 
        return categoryId; 
    } 
 
    public void setCategoryId(Long categoryId) { 
        this.categoryId = categoryId; 
    } 
 
    public String getCategory() { 
        return category; 
    } 
 
    public void setCategory(String category) { 
        this.category = category; 
    } 
 
    public String getDescription() { 
        return description; 
    } 
 
    public void setDescription(String description) { 
        this.description = description; 
    } 
} 

