package com.booklending.book.controller; 
 
import com.booklending.book.dto.BookDetailsDto; 
import com.booklending.book.dto.BookDto; 
import com.booklending.book.dto.PurchaseRequestDto; 
import com.booklending.book.dto.CommonResponse; 
import com.booklending.book.dto.DashboardResponseDto; 
import com.booklending.book.dto.NewBookRequestDto; 
import com.booklending.book.dto.NewBookResponseDto; 
import com.booklending.book.dto.SearchBookRequestDto; 
import com.booklending.book.dto.SearchBookResponseDto; 
import com.booklending.book.service.BookService; 
import com.booklending.book.service.LendService; 
import com.booklending.book.utils.ApplicationConstants; 
import org.apache.commons.lang.StringUtils; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
 
import java.time.LocalDate; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Objects; 
 
@CrossOrigin 
@RestController 
@RequestMapping("/book") 
public class BookController { 
 
    @Autowired 
    private BookService bookService; 
 
    @Autowired 
    private LendService lendService; 
 
    @DeleteMapping("/{bookId}") 
    public ResponseEntity<CommonResponse> deleteBook(@PathVariable Long bookId) { 
        return new ResponseEntity<>(bookService.deleteBook(bookId), HttpStatus.OK); 
    } 
 
    @PostMapping("/new") 
    public ResponseEntity<NewBookResponseDto> addNewBook(@RequestBody List<NewBookRequestDto> newBookRequestDtos) { 
        NewBookResponseDto newBookResponseDto = new NewBookResponseDto(); 
        if (newBookRequestDtos.isEmpty()) { 
            newBookResponseDto.setResponse( 
                    setCommonResponse(Boolean.FALSE, ApplicationConstants.NO_BOOKS_ADDED_FOR_LEND)); 
            return new ResponseEntity<>(newBookResponseDto, HttpStatus.OK); 
        } 
        return new ResponseEntity<>(bookService.addNewBook(newBookRequestDtos), HttpStatus.OK); 
    } 
 
    @PostMapping("/search") 
    public ResponseEntity<SearchBookResponseDto> searchBooks(@RequestBody SearchBookRequestDto searchBookRequestDto) { 
        List<BookDto> bookList = null; 
        List<List<BookDto>> bookDtoList = new ArrayList<>(); 
        SearchBookResponseDto searchBookResponseDto = new SearchBookResponseDto(); 
        if (Objects.nonNull(searchBookRequestDto.getTitle()) && 
                StringUtils.isNotBlank(searchBookRequestDto.getTitle())) { 
            bookList = bookService.getBooksByTitle(searchBookRequestDto.getTitle()); 
            bookDtoList.add(bookList); 
        } 
        if (Objects.nonNull(searchBookRequestDto.getCategory()) && 
                StringUtils.isNotBlank(searchBookRequestDto.getCategory())) { 
            bookList = bookService.getBooksByCategory(searchBookRequestDto.getCategory()); 
            bookDtoList.add(bookList); 
        } 
        if (Objects.nonNull(searchBookRequestDto.getAuthor()) && 
                StringUtils.isNotBlank(searchBookRequestDto.getAuthor())) { 
            bookList = bookService.getBooksByAuthor(searchBookRequestDto.getAuthor()); 
            bookDtoList.add(bookList); 
        } 
        if (bookList.isEmpty()) { 
            searchBookResponseDto.setCommonResponse( 
                    setCommonResponse(Boolean.FALSE, ApplicationConstants.NO_BOOKS_FOR_SEARCH_CRITERIA)); 
        } else { 
            searchBookResponseDto.setBooks(bookDtoList); 
            searchBookResponseDto.setCommonResponse( 
                    setCommonResponse(Boolean.TRUE, ApplicationConstants.EMPTY_STRING)); 
        } 
        return new ResponseEntity<>(searchBookResponseDto, HttpStatus.OK); 
    } 
 
    @GetMapping("/{customerId}/dashboard") 
    public ResponseEntity<DashboardResponseDto> getCustomerDashboard(@PathVariable Long customerId) { 
        return new ResponseEntity<>(lendService.getCustomerDashboard(customerId), HttpStatus.OK); 
    } 
 
    @GetMapping("/{bookId}") 
    public ResponseEntity<BookDetailsDto> getBookAndDamageDetails(@PathVariable Long bookId) { 
        BookDetailsDto bookDetailsDto = bookService.getBookAndDamageDetails(bookId); 
        if (Objects.isNull(bookDetailsDto.getBookDto())) { 
            bookDetailsDto.setCommonResponse( 
                    setCommonResponse(Boolean.FALSE, ApplicationConstants.INVALID_BOOK_ID)); 
        } else { 
            bookDetailsDto.setCommonResponse( 
                    setCommonResponse(Boolean.TRUE, ApplicationConstants.EMPTY_STRING)); 
        } 
        return new ResponseEntity<>(bookDetailsDto, HttpStatus.OK); 
    } 
 
    @PostMapping("/purchase") 
    public ResponseEntity<CommonResponse> addCart(@RequestBody PurchaseRequestDto purchaseRequestDto) { 
        return new ResponseEntity<>(setCommonResponse(Boolean.TRUE, lendService.addCart(purchaseRequestDto)), HttpStatus.OK); 
    } 
 
    @PutMapping("/{lendId}/renew") 
    public ResponseEntity<CommonResponse> renewBook(@PathVariable Long lendId) { 
        LocalDate extendDate = lendService.renewBook(lendId); 
        String response = ApplicationConstants.RENEW_SUCCESSFUL + extendDate; 
        return new ResponseEntity<>(setCommonResponse(Boolean.TRUE, response), HttpStatus.OK); 
    } 
 
    private CommonResponse setCommonResponse(Boolean result, String message) { 
        CommonResponse commonResponse = new CommonResponse(); 
        commonResponse.setMessage(message); 
        commonResponse.setResult(result); 
        return commonResponse; 
    } 
} 