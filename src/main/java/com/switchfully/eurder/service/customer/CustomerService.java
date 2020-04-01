package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        validateCustomerData(createCustomerDto);
        Customer customer = customerMapper.createCustomerDtoToCustomer(createCustomerDto);
        customerRepository.addCustomer(customer);
        return new CustomerDto(customer);
    }

    private void validateCustomerData(CreateCustomerDto createCustomerDto) {
        if (createCustomerDto.getFirstName() == null || createCustomerDto.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name not vacreate.");
        }
        if (createCustomerDto.getLastName() == null || createCustomerDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name not vcreated");
        }
        if (createCustomerDto.getEmail() == null || createCustomerDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email address not vcreated");
        }
        if (createCustomerDto.getCity() == null || createCustomerDto.getCity().isEmpty()
                || createCustomerDto.getStreet() == null || createCustomerDto.getStreet().isEmpty()
                || createCustomerDto.getHouseNumber() <= 0
                || createCustomerDto.getPostcode() <= 0) {
            throw new IllegalArgumentException("Address not valid");
        }
        if (createCustomerDto.getPhoneNumber() <= 0) {
            throw new IllegalArgumentException("Phone number not valid");
        }
    }
}
