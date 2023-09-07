package com.booklending.book.entity;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Index; 
import jakarta.persistence.JoinColumn; 
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.Table; 
 
@Entity 
@Table(name = "Book", indexes = { 
        @Index(name = "AUTHOR_INDEX", columnList = "author"), 
        @Index(name = "TITLE_INDEX", columnList = "title") 
}) 
public class Book { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column (name = "BOOK_ID") 
    private Long bookId; 
 
    @Column (name = "AUTHOR", nullable = false) 
    private String author; 
 
    @Column (name = "BOOK_NAME", nullable = false) 
    private String bookName; 
 
    @Column (name = "TITLE", nullable = false) 
    private String title; 
 
    @Column (name = "MRP", nullable = false) 
    private Double mrp; 
 
    @Column (name = "DESCRIPTION", nullable = false) 
    private String description; 
 
    @Column (name = "TOTAL_STOCK", nullable = false) 
    private Integer totalStock; 
 
    @Column (name = "RENTED", nullable = false) 
    private Integer rented; 
 
    @Column (name = "PER_DAY_PRICE", nullable = false) 
    private Double perDayPrice; 
 
    @Column (name = "CUSTOMER_ID", nullable = false) 
    private Long customerId; 
 
    @ManyToOne 
    @JoinColumn (name = "CATGEORY_ID", nullable = false) 
    private Category category; 
 
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
 
    public Integer getTotalStock() { 
        return totalStock; 
    } 
 
    public void setTotalStock(Integer totalStock) { 
        this.totalStock = totalStock; 
    } 
 
    public Integer getRented() { 
        return rented; 
    } 
 
    public void setRented(Integer rented) { 
        this.rented = rented; 
    } 
 
    public Double getPerDayPrice() { 
        return perDayPrice; 
    } 
 
    public void setPerDayPrice(Double perDayPrice) { 
        this.perDayPrice = perDayPrice; 
    } 
 
    public Long getCustomerId() { 
        return customerId; 
    } 
 
    public void setCustomerId(Long customerId) { 
        this.customerId = customerId; 
    } 
 
    public Long getBookId() { 
        return bookId; 
    } 
 
    public void setBookId(Long bookId) { 
        this.bookId = bookId; 
    } 
 
    public Category getCategory() { 
        return category; 
    } 
 
    public void setCategory(Category category) { 
        this.category = category; 
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
 
    public String getBookName() { 
        return bookName; 
    } 
 
    public void setBookName(String bookName) { 
        this.bookName = bookName; 
    } 
} 

