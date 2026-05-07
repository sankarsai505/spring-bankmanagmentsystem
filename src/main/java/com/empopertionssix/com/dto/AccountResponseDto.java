package com.empopertionssix.com.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {

    private Long accountId;
    private String accountType;
    private BigDecimal balance;
    // from Customer
    private String customerName;
    private String customerGender;
}