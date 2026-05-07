package com.empopertionssix.com.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.empopertionssix.com.dto.AccountCustomerDetails;
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
    @Query("SELECT new com.empopertionssix.com.dto.CustumerDetailsDto(c.customerName, c.customerGender, c.customerEmail) " +
           "FROM CustomerDetails c WHERE c.accounts IS EMPTY")
    List<CustumerDetailsDto> findCustomersWithoutAccountsDto();

    // ✅ FIXED: Native query for customers with accounts
    @Query(value = "SELECT c.customer_name, c.customer_gender, a.account_id, " +
                   "a.account_number, a.account_type, a.balance " +
                   "FROM customer_details c " +
                   "LEFT JOIN account a ON c.customer_account_id = a.customer_id",
           nativeQuery = true)
    List<AccountCustomerDetails> findByCustomersWithAccounts();

	Optional<CustomerDetails> findByCustomerEmail(String customerEmail);

	  @Query(value="SELECT c.state_name, COUNT(*) " +
	           "FROM customer_details c " +
	           "GROUP BY c.state_name",
	           nativeQuery = true)
	List<Object[]> countCustomersByStateName();
}