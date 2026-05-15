package com.empopertionssix.com.service;

import java.util.List;

import com.empopertionssix.com.dto.AddressDto;
import com.empopertionssix.com.dto.CustumerDetailsDto;
import com.empopertionssix.com.entity.CustomerDetails;
import com.empopertionssix.com.entity.CustomerGender;
public interface CustumerDetailsSerivceInterFace {

    // ✅ Create / Save
    CustomerDetails saveCustomer(CustomerDetails customerDetails);

    // ✅ Get All Customers
    List<CustumerDetailsDto> getAllCustomers();

    // ✅ Get By ID
    CustumerDetailsDto getCustomerById(Long customerId);

    // ✅ Update Customer
   CustomerDetails updateCustomerName(Long customerId,String customerName);

    // ✅ Delete Customer
    String deleteCustomerById(Long customerId);

    // ✅ Get Customers by Name (starts with / contains)
    List<CustumerDetailsDto> getByCustomerNames(String customerName);

    // ✅ Get Customers by Gender
    List<CustumerDetailsDto> getCustomersByGender(CustomerGender customerGender);
    //details customer names who don't have any account
    List<CustumerDetailsDto> getCustomersWithoutAccounts();
    
    List<CustumerDetailsDto> getCustomersWithAccounts();
    
    void sendRemindersToCustomersWithoutAccounts();
    
    void sendEmailToCustomerByEmail(String customerEmail);
    // number of customers in each state
    List<Object[]> countCustomersByStateName();
    
    // customers and city name and customerDetails
    List<AddressDto> getCustomerDetailsWithCityName(String cityName);
}