package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.service.customer.CustomerDto;
import com.switchfully.eurder.service.customer.CustomerMapper;
import com.switchfully.eurder.service.customer.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerServiceTest {

    @Test
    void createCustomer_addsCustomerToRepository() {
        //given
        CustomerRepository customerRepository = new CustomerRepository();
        CustomerMapper customerMapper = new CustomerMapper();
        CustomerService service = new CustomerService(customerRepository, customerMapper);
        Customer customer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber(100)
                .withEmail("hello@gmail.com")
                .build();
        CustomerDto customerDto = new CustomerDto(customer);
        //when
        service.createCustomer(customerDto);
        //then
        Assertions.assertThat(customerRepository.getCustomers().values().size()).isEqualTo(1);
    }

    @Test
    void createCustomer_withInvalidData_throwsException(){
        //given
        CustomerRepository customerRepository = new CustomerRepository();
        CustomerMapper customerMapper = new CustomerMapper();
        CustomerService service = new CustomerService(customerRepository, customerMapper);
        Customer customer = Customer.CustomerBuilder.newCustomer()
                .build();
        CustomerDto customerDto = new CustomerDto(customer);
        //then
        Assertions.assertThatThrownBy(()->service.createCustomer(customerDto)).isInstanceOf(IllegalArgumentException.class);
    }
}