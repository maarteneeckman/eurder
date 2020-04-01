package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.domain.Customer.CustomerBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer dtoToCustomer(CustomerDto customerDto) {
        return CustomerBuilder.newCustomer()
                .withFirstName(customerDto.getFirstName())
                .withLastName(customerDto.getLastName())
                .withAddress(customerDto.getAddress())
                .withEmail(customerDto.getEmail())
                .withPhoneNumber(customerDto.getPhoneNumber())
                .build();
    }
}
