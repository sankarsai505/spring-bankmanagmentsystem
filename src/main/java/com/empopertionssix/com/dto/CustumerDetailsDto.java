package com.empopertionssix.com.dto;

import com.empopertionssix.com.entity.CustomerGender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustumerDetailsDto {

	 private String customerName;
	 private CustomerGender customerGender;
	 private String customerEmail;

}
