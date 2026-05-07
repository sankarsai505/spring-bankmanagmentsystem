package com.empopertionssix.com.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Coustmeraddress {
	
	private String street;
	private String cityName;
	private String stateName;
	private String zipCode;
	private String countryName= "India";
}
