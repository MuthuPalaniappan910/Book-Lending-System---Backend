package com.booklending.book.dto; 
 
import java.util.List; 
 
public class BookDetailsDto { 
 
    private BookDto bookDto; 
    private Double perDayPrice; 
    private Boolean isBookDamaged; 
    private List<BookDamageDto> bookDamageDtoList; 
    private CommonResponse commonResponse; 
 
    public Double getPerDayPrice() { 
        return perDayPrice; 
    } 
 
    public void setPerDayPrice(Double perDayPrice) { 
        this.perDayPrice = perDayPrice; 
    } 
 
    public CommonResponse getCommonResponse() { 
        return commonResponse; 
    } 
 
    public void setCommonResponse(CommonResponse commonResponse) { 
        this.commonResponse = commonResponse; 
    } 
 
    public BookDto getBookDto() { 
        return bookDto; 
    } 
 
    public void setBookDto(BookDto bookDto) { 
        this.bookDto = bookDto; 
    } 
 
    public Boolean getBookDamaged() { 
        return isBookDamaged; 
    } 
 
    public void setBookDamaged(Boolean bookDamaged) { 
        isBookDamaged = bookDamaged; 
    } 
 
    public List<BookDamageDto> getBookDamageDtoList() { 
        return bookDamageDtoList; 
    } 
 
    public void setBookDamageDtoList(List<BookDamageDto> bookDamageDtoList) { 
        this.bookDamageDtoList = bookDamageDtoList; 
    } 
} 