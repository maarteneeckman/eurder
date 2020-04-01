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

    public void createCustomer(CustomerDto customerDto) {
        validateCustomerData(customerDto);
        Customer customer = customerMapper.dtoToCustomer(customerDto);
        customerRepository.addCustomer(customer);
    }

    private void validateCustomerData(CustomerDto customerDto) {
        if (customerDto.getFirstName() == null || customerDto.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name not valid.");
        }
        if (customerDto.getLastName() == null || customerDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name not valid");
        }
        if (customerDto.getEmail() == null || customerDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email address not valid");
        }
        if (customerDto.getAddress() == null) {
            throw new IllegalArgumentException("Address not valid");
        }
        //no check for phone number: valid formats differ per country
    }
}
