package com.booklending.book.entity;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.JoinColumn; 
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.Table; 
 
import java.time.LocalDate; 
 
@Entity 
@Table(name = "Lend") 
public class Lend { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column (name = "LEND_ID") 
    private Long lendId; 
 
    @Column (name = "START_DATE", nullable = false) 
    private LocalDate startDate; 
 
    @Column (name = "END_DATE", nullable = false) 
    private LocalDate endDate; 
 
    @Column (name = "FROM_CUSTOMER_ID", nullable = false) 
    private Long fromCustomerId; 
 
    @Column (name = "TO_CUSTOMER_ID", nullable = false) 
    private Long toCustomerId; 
 
    @Column (name = "PENALTY", nullable = false) 
    private Double penalty; 
 
    @ManyToOne 
    @JoinColumn (name = "BOOK_ID", nullable = false) 
    private Book book; 
 
    public Long getLendId() { 
        return lendId; 
    } 
 
    public void setLendId(Long lendId) { 
        this.lendId = lendId; 
    } 
 
    public LocalDate getStartDate() { 
        return startDate; 
    } 
 
    public void setStartDate(LocalDate startDate) { 
        this.startDate = startDate; 
    } 
 
    public LocalDate getEndDate() { 
        return endDate; 
    } 
 
    public void setEndDate(LocalDate endDate) { 
        this.endDate = endDate; 
    } 
 
    public Long getFromCustomerId() { 
        return fromCustomerId; 
    } 
 
    public void setFromCustomerId(Long fromCustomerId) { 
        this.fromCustomerId = fromCustomerId; 
    } 
 
    public Long getToCustomerId() { 
        return toCustomerId; 
    } 
 
    public void setToCustomerId(Long toCustomerId) { 
        this.toCustomerId = toCustomerId; 
    } 
 
    public Double getPenalty() { 
        return penalty; 
    } 
 
    public void setPenalty(Double penalty) { 
        this.penalty = penalty; 
    } 
 
    public Book getBook() { 
        return book; 
    } 
 
    public void setBook(Book book) { 
        this.book = book; 
    } 
} 

