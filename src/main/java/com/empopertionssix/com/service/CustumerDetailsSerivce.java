package com.empopertionssix.com.service;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empopertionssix.com.dto.AddressDto;
import com.empopertionssix.com.dto.CustumerDetailsDto;
import com.empopertionssix.com.entity.CustomerDetails;
import com.empopertionssix.com.entity.CustomerGender;
import com.empopertionssix.com.repo.CustumerDetailsRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustumerDetailsSerivce implements CustumerDetailsSerivceInterFace {

    private final CustumerDetailsRepo customerRepo;
    private final ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;
    // ✅ Save Customer
    @Override
    public CustomerDetails saveCustomer(CustomerDetails customerDetails) {
        return customerRepo.save(customerDetails);
    }

    // ✅ Get All Customers
    @Override
    public List<CustumerDetailsDto> getAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CustumerDetailsDto.class))
                .toList();
    }

    // ✅ Get By ID
    @Override
    public CustumerDetailsDto getCustomerById(Long customerId) {
        CustomerDetails customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        return modelMapper.map(customer, CustumerDetailsDto.class);
    }

    // ✅ Update Customer Name
    @Override
    public CustomerDetails updateCustomerName(Long customerId, String customerName) {

        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        CustomerDetails customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        customer.setCustomerName(customerName);

        CustomerDetails updated = customerRepo.save(customer);

        return updated;
    }
    // ✅ Delete Customer
    @Override
    public String deleteCustomerById(Long customerId) {
        if (!customerRepo.existsById(customerId)) {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }

        customerRepo.deleteById(customerId);
        return "Customer deleted successfully with id: " + customerId;
    }

    // ✅ Get By Name (Starts With)
    @Override
    public List<CustumerDetailsDto> getByCustomerNames(String customerName) {
        return customerRepo.findByCustomerNameStartingWith(customerName)
                .stream()
                .map(c -> modelMapper.map(c, CustumerDetailsDto.class))
                .toList();
    }

    // ✅ Get By Gender
    @Override
    public List<CustumerDetailsDto> getCustomersByGender(CustomerGender customerGender) {
        return customerRepo.findByCustomerGender(customerGender)
                .stream()
                .map(c -> modelMapper.map(c, CustumerDetailsDto.class))
                .toList();
    }

	@Override
	public List<CustumerDetailsDto> getCustomersWithoutAccounts() {
		
		return customerRepo.findCustomersWithoutAccounts()
				.stream()
				.map(c -> modelMapper.map(c, CustumerDetailsDto.class))
				.toList();
	}
	@Override
	@Transactional
	public void sendRemindersToCustomersWithoutAccounts() 
	{
		 List<CustomerDetails> customersWithoutAccounts = customerRepo.findCustomersWithoutAccounts();
		    
		    if (customersWithoutAccounts.isEmpty()) {
		        System.out.println("📭 No customers without accounts found.");
		        return;
		    }
		    
		    System.out.println("📧 Sending reminders to " + customersWithoutAccounts.size() + " customers...");
		    
		    // Send email to each customer
		    for (CustomerDetails customer : customersWithoutAccounts) {
		        try {
		            emailService.sendAccountReminderEmail(
		                    customer.getCustomerEmail(), 
		                    customer.getCustomerName()
		            );
		        } catch (Exception e) {
		            System.err.println("❌ Failed to send email to: " + customer.getCustomerEmail());
		        }
		    }
		    
		    System.out.println("✅ All reminder emails processed!");
			
}

	@Override
	@Transactional
	public void sendEmailToCustomerByEmail(String customerEmail) 
	{
		 CustomerDetails customer = customerRepo.findByCustomerEmail(customerEmail)
	                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + customerEmail));
		 
		 try {
	            emailService.sendAccountReminderEmail(
	                    customer.getCustomerEmail(), 
	                    customer.getCustomerName()
	            );
	        } catch (Exception e) {
	            System.err.println("❌ Failed to send email to: " + customer.getCustomerEmail());
	        }
		
	}

	@Override
	public List<Object[]> countCustomersByStateName() 
	{
        List<Object[]> results = customerRepo.countCustomersByStateName();
		for (Object[] result : results) {
			String stateName = (String) result[0];
			Long customerCount = (Long) result[1];
			System.out.println("State: " + stateName + ", Customer Count: " + customerCount);
		}
		return results;
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public List<CustumerDetailsDto> getCustomersWithAccounts() {
		// TODO Auto-generated method stub
		return customerRepo.findAll()
				.stream()
				.filter(c -> c.getAccounts() != null && !c.getAccounts().isEmpty())
				.map(c -> modelMapper.map(c, CustumerDetailsDto.class))
				.toList();
	}
	@Override
	public List<AddressDto> getCustomerDetailsWithCityName(
	        String cityName) {

	    return customerRepo
	            .findByCustomerAddressCityNameIgnoreCase(cityName)
	            .stream()
	            .map(c -> modelMapper.map(c, AddressDto.class))
	            .toList();
	}
}
