package com.booklending.book.service;

import com.booklending.book.dto.BookDamageDto; 
import com.booklending.book.dto.BookDetailsDto; 
import com.booklending.book.dto.BookDto; 
import com.booklending.book.dto.CommonResponse; 
import com.booklending.book.dto.NewBookRequestDto; 
import com.booklending.book.dto.NewBookResponseDto; 
import com.booklending.book.entity.Book; 
import com.booklending.book.entity.Category; 
import com.booklending.book.repository.BookRepository; 
import com.booklending.book.utils.ApplicationConstants; 
import org.springframework.beans.BeanUtils; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Objects; 
 
@Service 
public class BookServiceImpl implements BookService{ 
 
    @Autowired 
    private BookRepository bookRepository; 
 
    @Autowired 
    private BookDamageService bookDamageService; 
 
    @Autowired 
    private CategoryService categoryService; 
 
    @Autowired 
    private LendService lendService; 
 
    @Override 
     @Transactional 
     public CommonResponse deleteBook(Long bookId, Long customerId) { 
   	Book book = getBookDetailsByBookId(bookId); 
    	if (Objects.nonNull(book)) { 
        	if (book.getCustomerId().equals(customerId)) { 
            		bookDamageService.deleteBookDamages(book); 
            		lendService.deleteBookLend(book); 
            		bookRepository.deleteByBookId(bookId); 
            		return setCommonResponse(Boolean.TRUE, 						ApplicationConstants.BOOK_DELETED_SUCCESSFULLY); 
        	} 
        	return setCommonResponse(Boolean.FALSE, 				ApplicationConstants.DELETE_BOOK_AUTHORIZATION); 
    } 
    return setCommonResponse(Boolean.FALSE, 	ApplicationConstants.INVALID_BOOK_ID); 
}
 
    @Override 
    public NewBookResponseDto addNewBook(List<NewBookRequestDto> newBookRequestDtos) { 
        NewBookResponseDto newBookResponseDto = new NewBookResponseDto(); 
        newBookRequestDtos.forEach(newBookRequestDto -> { 
            Book book = new Book(); 
            Category category = categoryService.getCategoryDetails(newBookRequestDto.getCategory()); 
            if(Objects.nonNull(category)) { 
                BeanUtils.copyProperties(newBookRequestDto, book); 
                book.setRented(ApplicationConstants.ZERO); 
                book.setBookName(newBookRequestDto.getTitle()); 
                book.setPerDayPrice(calculatePerDayPrice(newBookRequestDto.getMrp())); 
                book.setCategory(category); 
                bookRepository.save(book); 
                if (!(newBookRequestDto.getNewBookDamageRequestDtoList().isEmpty())) { 
                    bookDamageService.saveDetailsOfBookDamage(newBookRequestDto.getNewBookDamageRequestDtoList(), book); 
                } 
            } 
        }); 
        newBookResponseDto.setResponse(setCommonResponse(Boolean.TRUE, ApplicationConstants.EMPTY_STRING)); 
        return newBookResponseDto; 
    } 
 
    @Override 
    public List<BookDto> getBooksByTitle(String title) { 
        List<Book> books = bookRepository.findAllByTitle(title); 
        return mapBooksToResponse(books); 
    } 
 
    @Override 
    public List<BookDto> getBooksByAuthor(String author) { 
        List<Book> books = bookRepository.findAllByAuthor(author); 
        return mapBooksToResponse(books); 
    } 
 
    @Override 
    public String getBookDetails(Long bookId) { 
        return getBookDetailsByBookId(bookId).getBookName(); 
    } 
 
    @Override 
    public BookDetailsDto getBookAndDamageDetails(Long bookId) { 
        BookDetailsDto bookDetailsDto = new BookDetailsDto(); 
        Book book = getBookDetailsByBookId(bookId); 
        if (Objects.isNull(book.getBookId())) { 
            return new BookDetailsDto(); 
        } 
        BookDto bookDto = new BookDto(); 
        BeanUtils.copyProperties(book, bookDto); 
        bookDto.setCategoryId(book.getCategory().getCategoryId()); 
        bookDto.setCategory(book.getCategory().getCategory()); 
        getDamagesOfBooks(book, bookDetailsDto); 
        bookDetailsDto.setBookDto(bookDto); 
        bookDetailsDto.setPerDayPrice(book.getPerDayPrice()); 
        return bookDetailsDto; 
    } 
 
    @Override 
    public Book updateRented(Long bookId) { 
        Book book = getBookDetailsByBookId(bookId); 
        book.setRented(book.getRented() + 1); 
        bookRepository.save(book); 
        return book; 
    } 
 
    @Override 
    public List<BookDto> getBooksByCategory(String category) { 
        List<Book> books = bookRepository.findAllByCategory(categoryService.getCategoryDetails(category)); 
        return mapBooksToResponse(books); 
    } 
 
    private Double calculatePerDayPrice(Double mrp) { 
        return mrp/ApplicationConstants.NO_OF_DAYS_FOR_LEND; 
    } 
 
    private List<BookDto> mapBooksToResponse(List<Book> books) { 
        List<BookDto> bookList = new ArrayList<>(); 
        if (!(books.isEmpty())) { 
            books.forEach (book -> { 
                BookDto bookDto = new BookDto(); 
                BeanUtils.copyProperties(book, bookDto); 
                bookDto.setCategory(book.getCategory().getCategory()); 
                bookDto.setCategoryId(book.getCategory().getCategoryId()); 
                if (book.getTotalStock() - book.getRented() == 0) { 
                    bookDto.setBookAvailable(Boolean.FALSE); 
                } else { 
                    bookDto.setBookAvailable(Boolean.TRUE); 
                } 
                bookList.add(bookDto); 
            }); 
        } 
        return bookList; 
    } 
 
    private void getDamagesOfBooks(Book book, BookDetailsDto bookDetailsDto) { 
        List<BookDamageDto> bookDamageDtoList = bookDamageService.getBookDamageList(book); 
        if (bookDamageDtoList.isEmpty()) { 
            bookDetailsDto.setBookDamaged(Boolean.FALSE); 
        } else { 
            bookDetailsDto.setBookDamaged(Boolean.TRUE); 
            bookDetailsDto.setBookDamageDtoList(bookDamageDtoList); 
        } 
    } 
 
    private Book getBookDetailsByBookId(Long bookId) { 
        return bookRepository.findByBookId(bookId); 
    } 
 
    private CommonResponse setCommonResponse(Boolean result, String message) { 
        CommonResponse commonResponse = new CommonResponse(); 
        commonResponse.setResult(result); 
        commonResponse.setMessage(message); 
        return commonResponse; 
    } 
} 
 
