package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.service.CustomerDto;
import com.switchfully.eurder.service.CustomerMapper;
import com.switchfully.eurder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerDto createCustomer(CustomerDto customerDto){
        customerService.createCustomer(customerDto);
        return customerDto;
    }
}
