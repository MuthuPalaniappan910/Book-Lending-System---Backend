package com.booklending.book.dto;

import java.util.List; 

public class SearchBookResponseDto { 
 
    private List<List<BookDto>> books; 
    private CommonResponse commonResponse; 
 
    public List<List<BookDto>> getBooks() { 
        return books; 
    } 
 
    public void setBooks(List<List<BookDto>> books) { 
        this.books = books; 
    } 
 
    public CommonResponse getCommonResponse() { 
        return commonResponse; 
    } 
 
    public void setCommonResponse(CommonResponse commonResponse) { 
        this.commonResponse = commonResponse; 
    } 


}
