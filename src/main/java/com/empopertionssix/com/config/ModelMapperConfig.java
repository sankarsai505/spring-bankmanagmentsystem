package com.empopertionssix.com.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.empopertionssix.com.dto.AddressDto;
import com.empopertionssix.com.entity.CustomerDetails;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CustomerDetails.class, AddressDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCustomerAddress().getCityName(), AddressDto::setCityName);
            mapper.map(src -> src.getCustomerAddress().getStateName(), AddressDto::setStateName);
        });

        return modelMapper;
    }
}