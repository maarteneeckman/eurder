package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    @Test
    void createCustomerDtoToCustomer_createsCorrectCustomer(){
        //given
        Customer expectedCustomer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber("100")
                .withEmail("hello@gmail.com")
                .build();
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(
                "John",
                "Doe",
                "hello@gmail.com",
                "Main street",
                10,
                "Metropolis",
                1000,
                "100");
        CustomerMapper customerMapper = new CustomerMapper();
        //when
        Customer actualCustomer = customerMapper.createCustomerDtoToCustomer(createCustomerDto);
        //then
        assertThat(actualCustomer).isEqualTo(expectedCustomer);
    }

}