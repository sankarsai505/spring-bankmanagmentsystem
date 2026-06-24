package com.empopertionssix.com.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empopertionssix.com.dto.AddressDto;
import com.empopertionssix.com.dto.CustumerDetailsDto;
import com.empopertionssix.com.entity.CustomerDetails;
import com.empopertionssix.com.entity.CustomerGender;
import com.empopertionssix.com.service.CustumerDetailsSerivce;
@RestController
@RequestMapping("/api/customers")
public class CustumerDetailController {

    private  CustumerDetailsSerivce customerService;
    public CustumerDetailController( CustumerDetailsSerivce customerService) 
    {
    	this.customerService=customerService;
    	
    }

    // ✅ Save Customer
    @PostMapping("/save")
    public ResponseEntity<CustomerDetails> saveCustomer(@RequestBody CustomerDetails customerDetails) {
        return ResponseEntity.ok(customerService.saveCustomer(customerDetails));
    }

    // ✅ Get All Customers
    @GetMapping("/byall")
    public ResponseEntity<List<CustumerDetailsDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // ✅ Get Customer By ID
    @GetMapping("/{id}")
    public ResponseEntity<CustumerDetailsDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // ✅ Update Customer Name
    @PutMapping("/{id}/updatename")
    public ResponseEntity<?> updateCustomerName(
            @PathVariable Long id,
            @RequestParam String customerName) {
    	customerService.updateCustomerName(id,customerName);
        return ResponseEntity.ok("deaails are updated");
    }

    // ✅ Delete Customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.deleteCustomerById(id));
    }

    // ✅ Get Customers By Name
    @GetMapping("/searchByStartingChar")
    public ResponseEntity<?> getByCustomerNames(@RequestParam String customerName) {

        List<CustumerDetailsDto> list = customerService.getByCustomerNames(customerName);

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with given starting characters");
        }

        return ResponseEntity.ok(list);
    }

    // ✅ Get Customers By Gender
    @GetMapping("/bygender")
    public ResponseEntity<List<CustumerDetailsDto>> getByGender(
            @RequestParam CustomerGender customerGender) {

        return ResponseEntity.ok(customerService.getCustomersByGender(customerGender));
    }
    
    // ✅ Get Customers Without Accounts
    @GetMapping("/withoutaccounts")
    public ResponseEntity<List<CustumerDetailsDto>> getCustomersWithoutAccounts() {
		return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomersWithoutAccounts());
	}
  // ✅ Get Customers With Accounts
    @GetMapping("/withaccounts")
    public ResponseEntity<List<CustumerDetailsDto>> getCustomersWithAccountsController() {

        List<CustumerDetailsDto> customers =
                customerService.getCustomersWithAccounts();

        return ResponseEntity.ok(customers);
    }
  @PostMapping("/sendreminders")
   public ResponseEntity<?> sendRemindersToCustomersWithoutAccounts()
  {
	customerService.sendRemindersToCustomersWithoutAccounts();
	return ResponseEntity.ok("✅ Reminder emails sent to customers without accounts!");
	  
  }
  @PostMapping("/sendemail")
  public ResponseEntity<?> sendEmail(@RequestParam String customerEmail) {

      if (customerEmail == null || customerEmail.isEmpty()) {
          return ResponseEntity.badRequest().body("Customer email is required");
      }

      try {
          customerService.sendEmailToCustomerByEmail(customerEmail);
          return ResponseEntity.ok("✅ Email sent successfully");
      } 
      catch (Exception e) {
    	    System.err.println("❌ Failed to send email to: " + customerEmail);
    	    e.printStackTrace();   // 🔥 THIS LINE IS MISSING
    	    throw new RuntimeException("Failed to send email", e);
    	}
      }
  @GetMapping("/count-by-state")
  public ResponseEntity<List<Object[]>> countByState() {
      try {
          List<Object[]> results = customerService.countCustomersByStateName();
          
          if (results.isEmpty()) {
              return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
          }
          
          return ResponseEntity.ok(results);
      } catch (Exception e) {
          System.err.println("Error fetching customer count by state: " + e.getMessage());
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
  }
  @GetMapping("/byCityName")
  public ResponseEntity<?> getByCityName(
          @RequestParam String cityName) {

      return ResponseEntity.ok(
              customerService
                      .getCustomerDetailsWithCityName(cityName));
  }
  
  @GetMapping("/byAccountOpeningDate")
 public ResponseEntity<?> findByAccountOpeningDate(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy")   LocalDate accountOpeningDate)
 {
	 try {
		  List<CustumerDetailsDto> customers = customerService.getCustomersByAccountOpeningDate(accountOpeningDate);
		  
		  if (customers.isEmpty()) {
			  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customers found with the given account opening date");
		  }
		  
		  return ResponseEntity.ok(customers);
	  } catch (Exception e) {
		  System.err.println("Error fetching customers by account opening date: " + e.getMessage());
		  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	  }
	  
  }
}
  