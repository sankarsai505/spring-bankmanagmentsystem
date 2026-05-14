package com.empopertionssix.com.service;

import java.math.BigDecimal;
import java.util.List;

import com.empopertionssix.com.dto.AccountCustomerDetails;
import com.empopertionssix.com.dto.AccountResponseDto;
import com.empopertionssix.com.entity.Account;
import com.empopertionssix.com.entity.TransactionType;

public interface AccountServiceInterface {

    // ✅ Create Account
   // AccountResponseDto createAccount(AccountResponseDto requestDto);

    // ✅ Get All Accounts
    // ✅ Get Account By ID
    // ✅ Update Account
    // ✅ Delete Account
    // ✅ Get Accounts By Balance
    List<AccountResponseDto> getByBalance(BigDecimal balance);
    // add deails of  throuh customer id
    Account addAccountDetails(Long customerId, Account account);
    // maximum balance accounts    
	BigDecimal depositAmmount(String accountNumber, BigDecimal amount, TransactionType transactionType);
	BigDecimal accountToAccountTransfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, TransactionType transactionType,String messge );
	
}
