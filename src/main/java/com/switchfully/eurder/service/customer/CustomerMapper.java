package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.Customer.CustomerBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer createCustomerDtoToCustomer(CreateCustomerDto dto) {
        return CustomerBuilder.newCustomer()
                .withFirstName(dto.getFirstName())
                .withLastName(dto.getLastName())
                .withAddress(new Address(dto.getStreet(),dto.getHouseNumber(),dto.getCity(),dto.getPostcode()))
                .withEmail(dto.getEmail())
                .withPhoneNumber(dto.getPhoneNumber())
                .build();
    }

}
