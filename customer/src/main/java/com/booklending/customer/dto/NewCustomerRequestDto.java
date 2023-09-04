package com.booklending.customer.dto; 
 
import com.booklending.customer.utils.ApplicationConstants; 
import jakarta.validation.constraints.Min; 
import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.Size; 
 
public class NewCustomerRequestDto { 
 
    @Min(10) 
    private Long mobileNumber; 
    @NotNull(message = ApplicationConstants.CUSTOMER_NAME_INVALID) 
    private String customerName; 
    @NotNull (message = ApplicationConstants.ADDRESS_INVALID) 
    private String address; 
    @NotNull (message = ApplicationConstants.CITY_INVALID) 
    private String city; 
    @NotNull (message = ApplicationConstants.STATE_INVALID) 
    private String state; 
    @NotNull (message = ApplicationConstants.PASSWORD_INVALID) 
    @Size(min = 8, max = 10, message = ApplicationConstants.PASSWORD_LENGTH_INVALID) 
    private String password; 
 
    public Long getMobileNumber() { 
        return mobileNumber; 
    } 
 
    public void setMobileNumber(Long mobileNumber) { 
        this.mobileNumber = mobileNumber; 
    } 
 
    public String getCustomerName() { 
        return customerName; 
    } 
 
    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    } 
 
    public String getAddress() { 
        return address; 
    } 
 
    public void setAddress(String address) { 
        this.address = address; 
    } 
 
    public String getCity() { 
        return city; 
    } 
 
    public void setCity(String city) { 
        this.city = city; 
    } 
 
    public String getState() { 
        return state; 
    } 
 
    public void setState(String state) { 
        this.state = state; 
    } 
 
    public String getPassword() { 
        return password; 
    } 
 
    public void setPassword(String password) { 
        this.password = password; 
    } 
} 
