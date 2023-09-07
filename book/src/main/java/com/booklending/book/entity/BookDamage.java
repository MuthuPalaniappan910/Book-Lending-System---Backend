package com.booklending.book.entity;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.JoinColumn; 
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.Table; 
 
@Entity 
@Table(name = "BookDamage") 
public class BookDamage { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column (name = "BOOK_DAMAGE_ID") 
    private Long bookDamageId; 
 
    @Column (name = "DAMAGE_DESCRIPTION", nullable = false) 
    private String damageDescription; 
 
    @ManyToOne 
    @JoinColumn (name = "BOOK_ID", nullable = false) 
    private Book book; 
 
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
 
    public Book getBook() { 
        return book; 
    } 
 
    public void setBook(Book book) { 
        this.book = book; 
    } 
} 

