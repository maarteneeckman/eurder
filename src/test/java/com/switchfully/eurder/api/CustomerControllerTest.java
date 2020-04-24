package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.Customer.CustomerBuilder;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.service.customer.CreateCustomerDto;
import com.switchfully.eurder.service.customer.CustomerDto;
import com.switchfully.eurder.service.customer.CustomerMapper;
import com.switchfully.eurder.service.customer.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


class CustomerControllerTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    class TestWithMockito {
        @Mock
        CustomerRepository customerRepository;

        @Test
        void createCustomer_whenCustomerIsValid_returnsCorrectDto() {
            //given
            CustomerController controller = new CustomerController(new CustomerService(customerRepository, new CustomerMapper()));
            Customer customer = CustomerBuilder.newCustomer()
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
            CustomerDto actual = controller.createCustomer(createCustomerDto);
            //then
            assertThat(actual).isEqualTo(customerDto);
        }

        @Test
        void getAllCustomers_returnsAllCustomersAsDtos() {
            //given
            CustomerController controller = new CustomerController(new CustomerService(customerRepository, new CustomerMapper()));

            Customer customer = CustomerBuilder.newCustomer()
                    .withFirstName("John")
                    .withLastName("Doe")
                    .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                    .withPhoneNumber("100")
                    .withEmail("hello@gmail.com")
                    .build();
            CustomerDto customerDto = new CustomerDto(customer);
            Customer customer2 = CustomerBuilder.newCustomer()
                    .withFirstName("Jane")
                    .withLastName("Doe")
                    .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                    .withPhoneNumber("100")
                    .withEmail("hello@gmail.com")
                    .build();
            CustomerDto customerDto2 = new CustomerDto(customer2);

            when(customerRepository.findAll()).thenReturn(Arrays.asList(customer, customer2));

            //then
            assertThat(controller.getAllCustomers()).containsExactlyInAnyOrder(customerDto, customerDto2);
        }

    }

    @Nested
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
    @DirtiesContext
    class TestWithSpringBootTest {
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
                    "100");
            WebTestClient.ResponseSpec response = webTestClient.post()
                    .uri("/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(createCustomerDto), CreateCustomerDto.class)
                    .exchange();
            response.expectStatus().isEqualTo(HttpStatus.CREATED);
        }

        @Test
        void getCustomerById_ifCustomerDoesNotExist_returnStatus400() {
            String fakeId = UUID.randomUUID().toString();
            WebTestClient.ResponseSpec response = this.webTestClient.get()
                    .uri("/customers/" + fakeId)
                    .exchange();
            response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void getCustomerById_ifCustomerExists_returnCorrectDto() {
            //given
            CreateCustomerDto createCustomerDto = new CreateCustomerDto(
                    "Jane",
                    "Doe",
                    "hello@gmail.com",
                    "Main street",
                    10,
                    "Metropolis",
                    1000,
                    "100");
            WebTestClient.ResponseSpec response1 = webTestClient.post()
                    .uri("/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(createCustomerDto), CreateCustomerDto.class)
                    .exchange();
            CustomerDto savedCustomerDto = response1
                    .expectBody(CustomerDto.class)
                    .returnResult()
                    .getResponseBody();

            //when
            WebTestClient.ResponseSpec response2 = this.webTestClient.get()
                    .uri("/customers/" + savedCustomerDto.getCustomerId().toString())
                    .exchange();
            response2.expectStatus().isEqualTo(HttpStatus.OK);
            CustomerDto actual = response2.expectBody(CustomerDto.class)
                    .returnResult()
                    .getResponseBody();

            //then
            assertThat(actual).isEqualTo(savedCustomerDto);
        }

    }

}