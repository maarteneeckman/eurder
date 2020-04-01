package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.Customer.CustomerBuilder;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.service.customer.CreateCustomerDto;
import com.switchfully.eurder.service.customer.CustomerDto;
import com.switchfully.eurder.service.customer.CustomerMapper;
import com.switchfully.eurder.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @Test
    void createCustomer_whenCustomerIsValid_returnsCorrectDto() {
        //given
        CustomerController controller = new CustomerController(new CustomerService(new CustomerRepository(), new CustomerMapper()));
        Customer customer = CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber(100)
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
                100);
        //when
        CustomerDto actual = controller.createCustomer(createCustomerDto);
        //then
        assertThat(actual).isEqualTo(customerDto);
    }

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void createCustomer_whenCustomerIsNotValid_returnStatus400() {
        WebTestClient.ResponseSpec response = this.webTestClient.post()
                .uri("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();
        response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createCustomer_whenCustomerIsValid_returnStatus201() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(
                "John",
                "Doe",
                "hello@gmail.com",
                "Main street",
                10,
                "Metropolis",
                1000,
                100);
        WebTestClient.ResponseSpec response = webTestClient.post()
                .uri("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createCustomerDto), CreateCustomerDto.class)
                .exchange();
        response.expectStatus().isEqualTo(HttpStatus.CREATED);
    }


}