package com.booklending.book.dto;

import java.util.List; 

public class NewBookRequestDto { 
 
    private String author; 
    private String title; 
    private Double mrp; 
    private String description; 
    private Integer totalStock; 
    private Long customerId; 
    private List<NewBookDamageRequestDto> newBookDamageRequestDtoList; 
    private String category; 
 
    public String getCategory() { 
        return category; 
    } 
 
    public void setCategory(String category) { 
        this.category = category; 
    } 
 
    public String getAuthor() { 
        return author; 
    } 
 
    public void setAuthor(String author) { 
        this.author = author; 
    } 
 
    public String getTitle() { 
        return title; 
    } 
 
    public void setTitle(String title) { 
        this.title = title; 
    } 
 
    public Double getMrp() { 
        return mrp; 
    } 
 
    public void setMrp(Double mrp) { 
        this.mrp = mrp; 
    } 
 
    public String getDescription() { 
        return description; 
    } 
 
    public void setDescription(String description) { 
        this.description = description; 
    } 
 
    public Integer getTotalStock() { 
        return totalStock; 
    } 
 
    public void setTotalStock(Integer totalStock) { 
        this.totalStock = totalStock; 
    } 
 
    public Long getCustomerId() { 
        return customerId; 
    } 
 
    public void setCustomerId(Long customerId) { 
        this.customerId = customerId; 
    } 
 
    public List<NewBookDamageRequestDto> getNewBookDamageRequestDtoList() { 
        return newBookDamageRequestDtoList; 
    } 
 
    public void setNewBookDamageRequestDtoList(List<NewBookDamageRequestDto> newBookDamageRequestDtoList) { 
        this.newBookDamageRequestDtoList = newBookDamageRequestDtoList; 
    } 
} 

