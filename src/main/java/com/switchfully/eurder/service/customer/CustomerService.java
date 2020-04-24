package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepositoryNoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CustomerService {

    private CustomerRepositoryNoDB customerRepository;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepositoryNoDB customerRepository, CustomerMapper customerMapper) {
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
            throw new IllegalArgumentException("First name not valid.");
        }
        if (createCustomerDto.getLastName() == null || createCustomerDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name not valid");
        }
        if (createCustomerDto.getEmail() == null || createCustomerDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email address not valid");
        }
        if (createCustomerDto.getCity() == null || createCustomerDto.getCity().isEmpty()
                || createCustomerDto.getStreet() == null || createCustomerDto.getStreet().isEmpty()
                || createCustomerDto.getHouseNumber() <= 0
                || createCustomerDto.getPostcode() <= 0) {
            throw new IllegalArgumentException("Address not valid");
        }
//        if (createCustomerDto.getPhoneNumber() <= 0) {
//            throw new IllegalArgumentException("Phone number not valid");
//        }
    }

    public Collection<CustomerDto> getAllCustomers() {
        return customerRepository.getCustomers().stream()
                .map(customer -> new CustomerDto(customer))
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(UUID id) {
        Customer customer = customerRepository.getCustomer(id);
        return new CustomerDto(customer);
    }
}
