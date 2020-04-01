package com.switchfully.eurder.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    @Test
    void addCustomer_addsCustomer(){
        //given
        CustomerRepository repo = new CustomerRepository();
        Customer customer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis",1000))
                .withPhoneNumber(100)
                .withEmail("hello@gmail.com")
                .build();
        UUID customerId = customer.getId();
        //when
        repo.addCustomer(customer);
        Customer actual = repo.getCustomer(customerId);
        //then
        Assertions.assertThat(actual).isEqualTo(customer);
    }

    @Test
    void addCustomer_ifCustomerAlreadyExists_throwException(){
        //given
        CustomerRepository repo = new CustomerRepository();
        Customer customer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis",1000))
                .withPhoneNumber(100)
                .withEmail("hello@gmail.com")
                .build();
        //when
        repo.addCustomer(customer);
        //then
        Assertions.assertThatThrownBy(() -> repo.addCustomer(customer)).isInstanceOf(CustomerNotUniqueException.class);
    }

    @Test
    void getCustomer_ifCustomerDoesNotExist_throwCustomerNotFoundException(){
        //given
        CustomerRepository repo = new CustomerRepository();
        //then
        Assertions.assertThatThrownBy(() -> repo.getCustomer(UUID.randomUUID())).isInstanceOf(CustomerNotFoundException.class);
    }

}