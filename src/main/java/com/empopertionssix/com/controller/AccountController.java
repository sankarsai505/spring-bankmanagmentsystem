package com.empopertionssix.com.controller;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.empopertionssix.com.dto.TransferRequest;
import com.empopertionssix.com.entity.Account;
import com.empopertionssix.com.entity.TransactionType;
import com.empopertionssix.com.service.AccountService;
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    
     public  AccountController(AccountService accountService)
     {
    	 this.accountService=accountService;
     } 
    @PostMapping("/addAccount/{customerId}")
   public ResponseEntity<?> addAccountDetails(@PathVariable Long customerId,@RequestBody Account account)
     {
    	Account savedAccount = accountService.addAccountDetails(customerId, account);	 
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
     }  
  @PostMapping("/deposit")
 public ResponseEntity<?> depositAmmount(@RequestParam String accountNumber,@RequestParam  BigDecimal amount, @RequestParam TransactionType transactionType)
  {
	  BigDecimal updatedBalance = accountService.depositAmmount(accountNumber, amount, transactionType);
	  return ResponseEntity.status(HttpStatus.OK).body("Amount deposited successfully. Updated balance: " + updatedBalance);	  
  }
  @PostMapping("/transfer")
  public ResponseEntity<String> accountToAccountTransfer(
          @RequestBody TransferRequest transferRequest,
          @RequestParam String message) {

      BigDecimal updatedBalance = accountService.accountToAccountTransfer(
              transferRequest.getFromAccountNumber(),
              transferRequest.getToAccountNumber(),
              transferRequest.getAmount(),
              TransactionType.ACCOUNT_TO_ACCOUNT_TRANSFER, // ✅ correct enum
              message
      );

      String responseMessage = "Amount is transferred from account "
              + transferRequest.getFromAccountNumber()
              + " to account "
              + transferRequest.getToAccountNumber()
              + ". Updated balance: "
              + updatedBalance;

      return ResponseEntity.ok(responseMessage);
	  }
}
