package com.empopertionssix.com.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCustomerDetails {
    private String customerName;
    private String customerGender;
    private Long accountId;
    private String accountNumber;      // ✅ Fixed typo
    private String accountType;
    private BigDecimal balance;
}