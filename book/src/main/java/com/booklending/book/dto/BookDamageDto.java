package com.booklending.book.dto; 
 
public class BookDamageDto { 
 
    private Long bookDamageId; 
    private String damageDescription; 
 
    public Long getBookDamageId() { 
        return bookDamageId; 
    } 
 
    public void setBookDamageId(Long bookDamageId) { 
        this.bookDamageId = bookDamageId; 
    } 
 
    public String getDamageDescription() { 
        return damageDescription; 
    } 
 
    public void setDamageDescription(String damageDescription) { 
        this.damageDescription = damageDescription; 
    } 
} 