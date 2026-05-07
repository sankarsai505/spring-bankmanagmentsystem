package com.empopertionssix.com.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.empopertionssix.com.dto.AccountCustomerDetails;
import com.empopertionssix.com.dto.AccountResponseDto;
import com.empopertionssix.com.entity.Account;
import com.empopertionssix.com.entity.CustomerDetails;
import com.empopertionssix.com.entity.Transaction;
import com.empopertionssix.com.entity.TransactionType;
import com.empopertionssix.com.repo.AccountRepo;
import com.empopertionssix.com.repo.CustumerDetailsRepo;
import com.empopertionssix.com.repo.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService implements AccountServiceInterface 
{

    private final TransactionRepository transactionRepository;
	private final AccountRepo accountRepo;
    private final ModelMapper modelMapper;
    private final CustumerDetailsRepo customerRepo;

    // ✅ Constructor
    public AccountService(AccountRepo accountRepo, ModelMapper modelMapper,
    		CustumerDetailsRepo customerRepo, TransactionRepository transactionRepository) {
        this.accountRepo = accountRepo;
        this.modelMapper = modelMapper;
        this.customerRepo = customerRepo;
		this.transactionRepository = transactionRepository;
    }

    // ✅ Method OUTSIDE constructor
    @Override
    public List<AccountResponseDto> getByBalance(BigDecimal balance) {

        List<Account> accounts = accountRepo.findByBalance(balance);

        if (accounts.isEmpty()) {
            throw new RuntimeException("No accounts found with balance: " + balance);
        }

        return accounts.stream()
                .map(acc -> {
                    AccountResponseDto dto = modelMapper.map(acc, AccountResponseDto.class);

                    if (acc.getCustomer() != null) {
                        dto.setCustomerName(acc.getCustomer().getCustomerName());
                        dto.setCustomerGender(acc.getCustomer().getCustomerGender().name());
                    }

                    return dto; // ✅ VERY IMPORTANT
                })
                .toList();
    }
    public Account addAccountDetails(Long customerId, Account account) 
    {
        
        // Fetch customer
        CustomerDetails customer = customerRepo.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        
        // Set customer to account
        account.setCustomer(customer);        
        // Save and return
        Transaction transaction = new Transaction();
	    transaction.setAmount(account.getBalance());
	    transaction.setType(TransactionType.CASH_DEPOSIT);
	    transaction.setTransactionDate(LocalDateTime.now());
	    transaction.setToAccount(account);
	    accountRepo.save(account);
	    transactionRepository.save(transaction);
        return accountRepo.save(account);
    }

	@Override
	public List<AccountCustomerDetails> findCustomersWithHighestBalance() {
		// TODO Auto-generated method stub
		return accountRepo.findCustomersWithHighestBalance();
	}
	@Override
	@Transactional
	public BigDecimal depositAmmount(String accountNumber, BigDecimal amount, TransactionType transactionType) {

	    Account account = accountRepo.findByAccountNumber(accountNumber);

	    if (account == null) {
	        throw new RuntimeException("Account not found");
	    }

	    System.out.println("Before: " + account.getBalance());

	    BigDecimal newBalance = account.getBalance().add(amount);
	    account.setBalance(newBalance);

	    System.out.println("After: " + account.getBalance());

	    Transaction transaction = new Transaction();
	    transaction.setAmount(amount);
	    transaction.setType(TransactionType.CASH_DEPOSIT); // ✅ FIXED
	    transaction.setTransactionDate(LocalDateTime.now());
	    transaction.setToAccount(account); // ✅ FIXED
	    accountRepo.save(account);
	    transactionRepository.save(transaction);
	    return newBalance;
	}

	@Transactional
	public BigDecimal accountToAccountTransfer(
	        String fromAccountNumber,
	        String toAccountNumber,
	        BigDecimal amount,
	        TransactionType transactionType,
	        String message) {

	    Account fromAccount = accountRepo.findByAccountNumber(fromAccountNumber);
	    Account toAccount = accountRepo.findByAccountNumber(toAccountNumber);

	    if (fromAccount == null || toAccount == null) {
	        throw new RuntimeException("One or both accounts not found");
	    }

	    if (fromAccount.getBalance().compareTo(amount) < 0) {
	        throw new RuntimeException("Insufficient funds in the source account");
	    }

	    // Debit & Credit
	    fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
	    toAccount.setBalance(toAccount.getBalance().add(amount));

	    // Save accounts
	    accountRepo.save(fromAccount);
	    accountRepo.save(toAccount);

	    // Save transaction
	    Transaction transaction = new Transaction();
	    transaction.setAmount(amount);
	    transaction.setType(transactionType);
	    transaction.setTransactionDate(LocalDateTime.now());
	    transaction.setFromAccount(fromAccount);
	    transaction.setToAccount(toAccount);
	    transactionRepository.save(transaction);

	    return fromAccount.getBalance();
	}
}
