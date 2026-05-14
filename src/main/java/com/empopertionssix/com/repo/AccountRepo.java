package com.empopertionssix.com.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empopertionssix.com.dto.AccountCustomerDetails;
import com.empopertionssix.com.entity.Account;
import com.empopertionssix.com.entity.CustomerDetails;

import jakarta.transaction.Transactional;

public interface AccountRepo  extends  JpaRepository<Account,Long>
{

	List<Account> findByBalance(BigDecimal balance);

	
	
	@Query(value = "SELECT c.customer_name, c.customer_gender, " +
            "a.account_id, a.account_number, a.account_type, max(a.balance) " +
            "FROM customer_details c " +
            "LEFT JOIN account a ON c.customer_account_id = a.customer_id " +
            "WHERE a.customer_id IS NOT NULL " +
            "GROUP BY c.customer_name, c.customer_gender, a.account_id, a.account_number, a.account_type " +
            "ORDER BY max(a.balance) DESC",
    nativeQuery = true)
	List<CustomerDetails> findCustomersWithHighestBalance();
	Account findByAccountNumber(String accountNumber);
	
	@Modifying
	@Transactional
	@Query("UPDATE Account a SET a.balance = a.balance + :amount WHERE a.accountNumber = :accountNumber")
	int depositAmount(@Param("accountNumber") String accountNumber,
	                  @Param("amount") BigDecimal amount);
}

/*private String customerName;
private String customerGender;
private Long accountId;
private String accountNumber;      // ✅ Fixed typo
private String accountType;
private BigDecimal balance;*/