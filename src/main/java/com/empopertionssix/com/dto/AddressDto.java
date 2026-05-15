package com.empopertionssix.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto 
{
	private String street;
	private String cityName;
	private String stateName;
	private String zipCode;
	private String customerName;

}
