package com.booklending.customer.service; 
 
import com.booklending.customer.dto.CommonResponse; 
import com.booklending.customer.dto.LoginRequestDto; 
import com.booklending.customer.dto.NewCustomerRequestDto; 
import com.booklending.customer.dto.UpdateCustomerProfileRequestDto; 
import com.booklending.customer.entity.Customer; 
import com.booklending.customer.repository.CustomerRepository; 
import com.booklending.customer.utils.ApplicationConstants; 
import org.springframework.beans.BeanUtils; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service; 
 
import java.util.Objects; 
 
@Service 
public class CustomerServiceImpl implements CustomerService { 
 
    @Autowired 
    private CustomerRepository customerRepository; 
 
    @Autowired 
    private PasswordEncoder passwordEncoder; 
 
    @Override 
    public CommonResponse customerRegister(NewCustomerRequestDto newCustomerRequestDto) { 
        Customer customer = new Customer(); 
        BeanUtils.copyProperties (newCustomerRequestDto, customer); 
        customer.setPassword(encodePassword(newCustomerRequestDto.getPassword())); 
        customerRepository.save(customer); 
        return setCommonResponse(Boolean.TRUE, String.valueOf(customer.getCustomerId())); 
    } 
 
    @Override 
    public CommonResponse customerLogin(LoginRequestDto loginRequestDto) { 
        Customer customer = customerRepository.findByMobileNumber(loginRequestDto.getMobileNumber()); 
        if (Objects.isNull(customer)) { 
            return setCommonResponse(Boolean.FALSE, ApplicationConstants.CUSTOMER_REGISTRATION_MESSAGE); 
        } 
        if (passwordEncoder.matches(loginRequestDto.getPassword(), customer.getPassword())) { 
            return setCommonResponse(Boolean.TRUE, String.valueOf(customer.getCustomerId())); 
        } 
        return setCommonResponse(Boolean.FALSE, ApplicationConstants.INCORRECT_CREDENTIALS); 
 
    } 
 
    @Override 
    public CommonResponse customerProfileUpdate(Long customerId, UpdateCustomerProfileRequestDto updateCustomerProfileRequestDto) { 
        Customer customer = isCustomerPresent(customerId); 
        if (Objects.isNull(customer)) { 
            return setCommonResponse(Boolean.FALSE, ApplicationConstants.CUSTOMER_NOT_FOUND); 
        } 
        BeanUtils.copyProperties(updateCustomerProfileRequestDto, customer); 
        customerRepository.save(customer); 
        return setCommonResponse(Boolean.TRUE, ApplicationConstants.CUSTOMER_PROFILE_UPDATED_SUCCESSFULLY); 
    } 
 
    @Override 
    public Boolean validateCustomer(Long customerId) { 
        Customer customer = isCustomerPresent(customerId); 
        return Objects.isNull(customer) ? Boolean.FALSE : Boolean.TRUE; 
    } 
 
    public Customer isCustomerPresent(Long customerId) { 
        return customerRepository.findByCustomerId(customerId); 
    } 
 
    private String encodePassword(String password) { 
        return passwordEncoder.encode(password); 
    } 
 
    private CommonResponse setCommonResponse(Boolean result, String message) { 
        CommonResponse commonResponse = new CommonResponse(); 
        commonResponse.setMessage(message); 
        commonResponse.setResult(result); 
        return commonResponse; 
    } 
 
} 
