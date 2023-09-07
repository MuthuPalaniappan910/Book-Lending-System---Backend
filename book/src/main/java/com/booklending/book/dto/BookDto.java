package com.booklending.book.dto;

public class BookDto {
	
	private Long bookId; 
    private String title; 
    private Long categoryId; 
    private  String author; 
    private String description; 
    private String category; 
    private Boolean isBookAvailable; 
 
    public Boolean getBookAvailable() { 
        return isBookAvailable; 
    } 
 
    public void setBookAvailable(Boolean bookAvailable) { 
        isBookAvailable = bookAvailable; 
    } 
 
    public String getCategory() { 
        return category; 
    } 
 
    public void setCategory(String category) { 
        this.category = category; 
    } 
 
    public Long getBookId() { 
        return bookId; 
    } 
 
    public void setBookId(Long bookId) { 
        this.bookId = bookId; 
    } 
 
    public String getTitle() { 
        return title; 
    } 
 
    public void setTitle(String title) { 
        this.title = title; 
    } 
 
    public Long getCategoryId() { 
        return categoryId; 
    } 
 
    public void setCategoryId(Long categoryId) { 
        this.categoryId = categoryId; 
    } 
 
    public String getAuthor() { 
        return author; 
    } 
 
    public void setAuthor(String author) { 
        this.author = author; 
    } 
 
    public String getDescription() { 
        return description; 
    } 
 
    public void setDescription(String description) { 
        this.description = description; 
    } 


}
