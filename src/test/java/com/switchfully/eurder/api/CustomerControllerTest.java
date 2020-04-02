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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
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


    @Test
    void getAllCustomers_returnsAllCustomersAsDtos() {
        //given
        CustomerRepository customerRepository = new CustomerRepository();
        CustomerController controller = new CustomerController(new CustomerService(customerRepository, new CustomerMapper()));

        Customer customer = CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber(100)
                .withEmail("hello@gmail.com")
                .build();
        CustomerDto customerDto = new CustomerDto(customer);
        Customer customer2 = CustomerBuilder.newCustomer()
                .withFirstName("Jane")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber(100)
                .withEmail("hello@gmail.com")
                .build();
        CustomerDto customerDto2 = new CustomerDto(customer2);

        customerRepository.addCustomer(customer);
        customerRepository.addCustomer(customer2);

        //then
        Assertions.assertThat(controller.getAllCustomers()).containsExactlyInAnyOrder(customerDto, customerDto2);
    }

    @Test
    void getCustomerById_ifCustomerDoesNotExist_returnStatus400() {
        String fakeId = UUID.randomUUID().toString();
        WebTestClient.ResponseSpec response = this.webTestClient.get()
                .uri("/customers/"+ fakeId)
                .exchange();
        response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getCustomerById_ifCustomerExists_returnCorrectDto(){
        //given
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(
                "Jane",
                "Doe",
                "hello@gmail.com",
                "Main street",
                10,
                "Metropolis",
                1000,
                100);
        WebTestClient.ResponseSpec response1 = webTestClient.post()
                .uri("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createCustomerDto), CreateCustomerDto.class)
                .exchange();
        CustomerDto customerDto = response1
                .expectBody(CustomerDto.class)
                .returnResult()
                .getResponseBody();

        WebTestClient.ResponseSpec response2 = this.webTestClient.get()
                .uri("/customers/" + customerDto.getCustomerId().toString())
                .exchange();
        response2.expectStatus().isEqualTo(HttpStatus.OK);
        CustomerDto actual = response2.expectBody(CustomerDto.class)
                .returnResult()
                .getResponseBody();
        CustomerDto expected = new CustomerDto(
                customerDto.getCustomerId(),
                "Jane",
                "Doe",
                "hello@gmail.com",
                new Address("Main street",10,"Metropolis",1000),
                100);
        assertThat(actual).isEqualTo(expected);
    }

}