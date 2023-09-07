package com.booklending.book.service;

import com.booklending.book.dto.BooksFurnishedDto; 
import com.booklending.book.dto.BooksRentedDto; 
import com.booklending.book.dto.GetCustomerResponseDto; 
import com.booklending.book.dto.PurchaseRequestDto; 
import com.booklending.book.dto.DashboardResponseDto; 
import com.booklending.book.entity.Book; 
import com.booklending.book.entity.Lend; 
import com.booklending.book.repository.LendRepository; 
import com.booklending.book.utils.ApplicationConstants; 
import com.twilio.Twilio; 
import com.twilio.rest.api.v2010.account.Message; 
import com.twilio.type.PhoneNumber; 
import jakarta.inject.Inject; 
import org.springframework.beans.BeanUtils; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.scheduling.annotation.Scheduled; 
import org.springframework.stereotype.Service; 
 
import java.time.LocalDate; 
import java.time.temporal.ChronoUnit; 
import java.util.ArrayList; 
import java.util.List; 
 
@Service 
public class LendServiceImpl implements LendService { 
 
    @Autowired 
    private LendRepository lendRepository; 
 
    @Autowired 
    private BookService bookService; 
    @Inject 
    private CustomerApplicationService customerApplicationService; 
 
    @Value("${twilio_account_sid}") 
    private String twilioAccountSid; 
 
    @Value("${twilio_auth_token}") 
    private String twilioAuthToken; 
 
    @Value("${twilio_phone_num}") 
    private String twilioPhoneNum; 
 
    @Override 
    public DashboardResponseDto getCustomerDashboard(Long customerId) { 
        List<Lend> booksFurnished = getBooksFurnished(customerId); 
        List<Lend> booksRented = getBooksRented(customerId); 
        if (booksFurnished.isEmpty() && booksRented.isEmpty()) { 
            return null; 
        } 
        DashboardResponseDto dashboardResponseDto = new DashboardResponseDto(); 
        List<BooksFurnishedDto> booksFurnishedDtoList = new ArrayList<>(); 
        List<BooksRentedDto> booksRentedDtoList = new ArrayList<>(); 
        if (!(booksFurnished.isEmpty())) { 
            setBooksFurnishedResponse(booksFurnished, booksFurnishedDtoList); 
        } 
        if (!(booksRented.isEmpty())) { 
            setBooksRentResponse(booksRented, booksRentedDtoList); 
        } 
        dashboardResponseDto.setBooksLent(booksFurnishedDtoList); 
        dashboardResponseDto.setBooksRent(booksRentedDtoList); 
        return dashboardResponseDto; 
    } 
 
    @Override 
    public String addCart(PurchaseRequestDto purchaseRequestDto) { 
        Book book = bookService.updateRented(purchaseRequestDto.getBookId()); 
        Lend lend = new Lend(); 
        lend.setFromCustomerId(book.getCustomerId()); 
        lend.setStartDate(LocalDate.now()); 
        lend.setEndDate(LocalDate.now().plusDays(purchaseRequestDto.getNumberOfDays())); 
        lend.setToCustomerId(purchaseRequestDto.getCustomerId()); 
        lend.setBook(book); 
        lend.setPenalty(ApplicationConstants.PENALTY_DEFAULT); 
        lendRepository.saveAndFlush(lend); 
        return ApplicationConstants.PAID + 
                book.getPerDayPrice() * purchaseRequestDto.getNumberOfDays() 
                + ApplicationConstants.ORDER_SUCCESSFUL; 
    } 
 
    @Override 
    public LocalDate renewBook(Long lendId) { 
        Lend lend = lendRepository.findByLendId(lendId); 
        lend.setEndDate(lend.getEndDate().plusDays(ApplicationConstants.RENEW_DAYS)); 
        lendRepository.save(lend); 
        return lend.getEndDate(); 
    } 
 
    @Scheduled(cron = "0 0 0 * * *") 
    public void sendNotificationsToCustomers() { 
        List<Lend> notificationList = lendRepository.findByEndDate(LocalDate.now().plusDays(2)); 
        if (!(notificationList.isEmpty())) { 
            notificationList.forEach (notification -> { 
                GetCustomerResponseDto customer = customerApplicationService.getCustomerDetails(notification.getToCustomerId()); 
                String userMobileNumber = "+91" + customer.getMobileNumber(); 
                Twilio.init(twilioAccountSid, twilioAuthToken); 
                Message.creator(new PhoneNumber(userMobileNumber), new PhoneNumber(twilioPhoneNum), 
                        ApplicationConstants.HI 
                                + customer.getCustomerName() 
                                + ApplicationConstants.BOOK_MESSAGE 
                                + notification.getBook().getBookName() 
                                + ApplicationConstants.NOTIFICATION) 
                        .create(); 
            }); 
        } 
    } 
 
    @Override 
    public void deleteBookLend(Book book) { 
        lendRepository.deleteAllByBook(book); 
    } 
 
    private List<Lend> getBooksFurnished(Long customerId) { 
        return lendRepository.findAllByFromCustomerId(customerId); 
    } 
 
    private List<Lend> getBooksRented(Long customerId) { 
        return lendRepository.findAllByToCustomerId(customerId); 
    } 
 
    private String getBookDetails(Lend bookRented) { 
        return bookService.getBookDetails(bookRented.getBook().getBookId()); 
    } 
 
    private void setBooksRentResponse(List<Lend> booksRented, List<BooksRentedDto> booksRentedDtoList) { 
        booksRented.forEach (bookRented -> { 
            BooksRentedDto booksRentedDto = new BooksRentedDto(); 
            BeanUtils.copyProperties(bookRented, booksRentedDto); 
            booksRentedDto.setTitle(getBookDetails(bookRented)); 
            if (ChronoUnit.DAYS.between(LocalDate.now(), bookRented.getEndDate()) >= ApplicationConstants.DATE_DIFF_RENEW_DEFAULT) { 
                booksRentedDto.setRenewable(Boolean.TRUE); 
                booksRentedDto.setAdditionalAmountForRenew( 
                        (bookRented.getBook().getPerDayPrice() + ApplicationConstants.RENEW_DEFAULT_ADD_ON) * ApplicationConstants.RENEW_DAYS); 
            } else { 
                booksRentedDto.setRenewable(Boolean.FALSE); 
            } 
            booksRentedDto.setLendId(bookRented.getLendId()); 
            booksRentedDtoList.add(booksRentedDto); 
        }); 
    } 
 
    private void setBooksFurnishedResponse(List<Lend> booksFurnished, List<BooksFurnishedDto> booksFurnishedDtoList) { 
        booksFurnished.forEach(bookFurnished -> { 
            BooksFurnishedDto booksFurnishedDto = new BooksFurnishedDto(); 
            BeanUtils.copyProperties(bookFurnished, booksFurnishedDto); 
            booksFurnishedDto.setTitle(getBookDetails(bookFurnished)); 
            booksFurnishedDtoList.add(booksFurnishedDto); 
        }); 
    } 
} 

