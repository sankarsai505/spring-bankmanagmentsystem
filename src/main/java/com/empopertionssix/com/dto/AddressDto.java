package com.empopertionssix.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto 
{
    private String customerName;
    private String cityName;
    private String stateName;


}
