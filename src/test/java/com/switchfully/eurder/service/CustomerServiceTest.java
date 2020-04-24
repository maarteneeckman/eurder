package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepositoryNoDB;
import com.switchfully.eurder.service.customer.CreateCustomerDto;
import com.switchfully.eurder.service.customer.CustomerDto;
import com.switchfully.eurder.service.customer.CustomerMapper;
import com.switchfully.eurder.service.customer.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerServiceTest {

    @Test
    void createCustomer_addsCustomerToRepository() {
        //given
        CustomerRepositoryNoDB customerRepository = new CustomerRepositoryNoDB();
        CustomerMapper customerMapper = new CustomerMapper();
        CustomerService service = new CustomerService(customerRepository, customerMapper);
        Customer customer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber("100")
                .withEmail("hello@gmail.com")
                .build();
        CustomerDto customerDto = new CustomerDto(customer);
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(
                "John",
                "Doe",
                "hello@gmail.com",
                "Main street",
                10,
                "Metropolis",
                1000,
                "100");
        //when
        service.createCustomer(createCustomerDto);
        //then
        Assertions.assertThat(customerRepository.getCustomers().size()).isEqualTo(1);
    }

    @Test
    void createCustomer_withInvalidData_throwsException(){
        //given
        CustomerRepositoryNoDB customerRepository = new CustomerRepositoryNoDB();
        CustomerMapper customerMapper = new CustomerMapper();
        CustomerService service = new CustomerService(customerRepository, customerMapper);
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("","","","",0,"",0,"0");
        //then
        Assertions.assertThatThrownBy(()->service.createCustomer(createCustomerDto)).isInstanceOf(IllegalArgumentException.class);
    }
}