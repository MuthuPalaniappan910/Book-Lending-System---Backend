package com.booklending.customer.entity; 
 
import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table; 
 
@Entity 
@Table(name = "Customer") 
public class Customer { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column (name = "CUSTOMER_ID") 
    private Long customerId; 
 
    @Column (name = "CUSTOMER_NAME", nullable = false) 
    private String customerName; 
 
    @Column (name = "MOBILE_NUMBER", nullable = false) 
    private Long mobileNumber; 
 
    @Column (name = "ADDRESS", nullable = false) 
    private String address; 
 
    @Column (name = "CITY", nullable = false) 
    private String city; 
 
    @Column (name = "STATE", nullable = false) 
    private String state; 
 
    @Column (name = "PASSWORD", nullable = false) 
    private String password; 
 
    public Long getMobileNumber() { 
        return mobileNumber; 
    } 
 
    public void setMobileNumber(Long mobileNumber) { 
        this.mobileNumber = mobileNumber; 
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
 
    public Long getCustomerId() { 
        return customerId; 
    } 
 
    public void setCustomerId(Long customerId) { 
        this.customerId = customerId; 
    } 
 
    public String getCustomerName() { 
        return customerName; 
    } 
 
    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    } 
 
    public String getPassword() { 
        return password; 
    } 
 
    public void setPassword(String password) { 
        this.password = password; 
    } 
} 
