package com.booklending.customer.dto; 
 
import java.util.List; 
 
public class DashboardResponseDto { 
 
    private List<BooksFurnishedDto> booksLent; 
    private List<BooksRentedDto> booksRent; 
    private CommonResponse commonResponse; 
 
    public List<BooksFurnishedDto> getBooksLent() { 
        return booksLent; 
    } 
 
    public void setBooksLent(List<BooksFurnishedDto> booksLent) { 
        this.booksLent = booksLent; 
    } 
 
    public List<BooksRentedDto> getBooksRent() { 
        return booksRent; 
    } 
 
    public void setBooksRent(List<BooksRentedDto> booksRent) { 
        this.booksRent = booksRent; 
    } 
 
    public CommonResponse getCommonResponse() { 
        return commonResponse; 
    } 
 
    public void setCommonResponse(CommonResponse commonResponse) { 
        this.commonResponse = commonResponse; 
    } 
} 