package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.Customer.CustomerBuilder;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.service.customer.CustomerDto;
import com.switchfully.eurder.service.customer.CustomerMapper;
import com.switchfully.eurder.service.customer.CustomerService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CustomerControllerTest {

    @Test
    void createCustomer_whenCustomerIsValid_returnsCorrectDto(){
        //given
        CustomerController controller = new CustomerController(new CustomerService(new CustomerRepository(), new CustomerMapper()));
        Customer customer = CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis",1000))
                .withPhoneNumber(100)
                .withEmail("hello@gmail.com")
                .build();
        CustomerDto customerDto = new CustomerDto(customer);
        //when
        CustomerDto actual = controller.createCustomer(customerDto);
        //then
        assertThat(actual).isEqualTo(customerDto);
    }

}