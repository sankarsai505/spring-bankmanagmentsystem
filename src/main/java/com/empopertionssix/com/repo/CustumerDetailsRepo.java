package com.empopertionssix.com.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empopertionssix.com.dto.CustumerDetailsDto;
import com.empopertionssix.com.entity.CustomerDetails;
import com.empopertionssix.com.entity.CustomerGender;

public interface CustumerDetailsRepo extends JpaRepository<CustomerDetails, Long> {

    // ✅ Return Entity
    List<CustomerDetails> findByCustomerNameStartingWith(String customerName);

    // ✅ Return Entity by Gender
    List<CustomerDetails> findByCustomerGender(CustomerGender customerGender);

    // ✅ FIXED: Return List<CustomerDetails> (Entity, not DTO)
    List<CustomerDetails> findByAccountsIsEmpty();

    // ✅ FIXED: Alternative using @Query with correct return type
    @Query("SELECT c FROM CustomerDetails c WHERE c.accounts IS EMPTY")
    List<CustomerDetails> findCustomersWithoutAccounts();

    // ✅ For DTO mapping (if you want DTO instead)
    @Query("SELECT new com.empopertionssix.com.dto.CustumerDetailsDto(c.customerName, c.customerGender, c.customerEmail,c. accountOpeningDate) " +
           "FROM CustomerDetails c WHERE c.accounts IS EMPTY")
    List<CustumerDetailsDto> findCustomersWithoutAccountsDto();

    // ✅ FIXED: Native query for customers with accounts
	Optional<CustomerDetails> findByCustomerEmail(String customerEmail);

	  @Query(value="SELECT c.state_name, COUNT(*) " +
	           "FROM customer_details c " +
	           "GROUP BY c.state_name",
	           nativeQuery = true)
	List<Object[]> countCustomersByStateName();

	List<CustomerDetails>
	findByCustomerAddressCityNameIgnoreCase(String cityName);
	
	// find by account opening date
	@Query("""
			SELECT new com.empopertionssix.com.dto.CustumerDetailsDto(
			    c.customerName,
			    c.customerGender,
			    c.customerEmail,
			    c.accountOpeningDate
			)
			FROM CustomerDetails c
			WHERE c.accountOpeningDate = :accountOpeningDate
			""")
			List<CustumerDetailsDto> findByAccountOpeningDate(
			        @Param("accountOpeningDate") LocalDate accountOpeningDate);
	
	
	
}











/*private String customerName;
private String cityName;
private String stateName;*/

/*	 private String customerName;
	 private CustomerGender customerGender;
	 private String customerEmail;
	 private LocalDate accountOpeningDate;*/



