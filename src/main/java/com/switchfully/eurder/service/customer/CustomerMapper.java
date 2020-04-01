package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.Customer.CustomerBuilder;
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
