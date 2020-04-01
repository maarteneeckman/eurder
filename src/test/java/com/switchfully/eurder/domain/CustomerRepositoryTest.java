package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.Customer.CustomerBuilder;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.domain.exceptions.CustomerNotUniqueException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerRepositoryTest {

    @Test
    void addCustomer_addsCustomer(){
        //given
        CustomerRepository repo = new CustomerRepository();
        Customer customer = CustomerBuilder.newCustomer()
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
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void addCustomer_ifCustomerAlreadyExists_throwException(){
        //given
        CustomerRepository repo = new CustomerRepository();
        Customer customer = CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis",1000))
                .withPhoneNumber(100)
                .withEmail("hello@gmail.com")
                .build();
        //when
        repo.addCustomer(customer);
        //then
        assertThatThrownBy(() -> repo.addCustomer(customer)).isInstanceOf(CustomerNotUniqueException.class);
    }

    @Test
    void getCustomer_ifCustomerDoesNotExist_throwCustomerNotFoundException(){
        //given
        CustomerRepository repo = new CustomerRepository();
        //then
        assertThatThrownBy(() -> repo.getCustomer(UUID.randomUUID())).isInstanceOf(CustomerNotFoundException.class);
    }

}