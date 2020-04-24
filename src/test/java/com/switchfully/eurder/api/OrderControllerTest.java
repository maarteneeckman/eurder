package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepositoryNoDB;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.service.customer.CreateCustomerDto;
import com.switchfully.eurder.service.customer.CustomerDto;
import com.switchfully.eurder.service.item.CreateItemDto;
import com.switchfully.eurder.service.item.ItemDto;
import com.switchfully.eurder.service.order.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
class OrderControllerTest {

    @Test
    void placeOrder_returnsCorrectDto() {
        //given
        CustomerRepositoryNoDB customerRepository = new CustomerRepositoryNoDB();
        ItemRepository itemRepository = new ItemRepository();
        OrderMapper orderMapper = new OrderMapper(customerRepository, itemRepository);
        OrderService orderService = new OrderService(orderMapper);
        OrderController orderController = new OrderController(orderService);

        Customer customer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber("100")
                .withEmail("hello@gmail.com")
                .build();
        UUID customerId = customer.getId();
        customerRepository.addCustomer(customer);

        Item item = new Item(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        UUID itemId = item.getId();
        itemRepository.addItem(item);

        int amount = 5;

        CreateItemGroupDto createItemGroupDto = new CreateItemGroupDto(itemId, amount);
        CreateOrderDto createOrderDto = new CreateOrderDto(customerId, List.of(createItemGroupDto));

        ItemGroupDto itemGroupDto = new ItemGroupDto(itemId, amount, LocalDate.now().plusDays(1), 62.5);
        OrderDto expected = new OrderDto(UUID.randomUUID(), customerId, List.of(itemGroupDto));

        //when
        OrderDto actual = orderController.placeOrder(createOrderDto);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void placeOrder_ifItemNotInStock_returnsCorrectDto() {
        //given
        CustomerRepositoryNoDB customerRepository = new CustomerRepositoryNoDB();
        ItemRepository itemRepository = new ItemRepository();
        OrderMapper orderMapper = new OrderMapper(customerRepository, itemRepository);
        OrderService orderService = new OrderService(orderMapper);
        OrderController orderController = new OrderController(orderService);

        Customer customer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber("100")
                .withEmail("hello@gmail.com")
                .build();
        UUID customerId = customer.getId();
        customerRepository.addCustomer(customer);

        Item item = new Item(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                0);
        UUID itemId = item.getId();
        itemRepository.addItem(item);

        int amount = 5;

        CreateItemGroupDto createItemGroupDto = new CreateItemGroupDto(itemId, amount);
        CreateOrderDto createOrderDto = new CreateOrderDto(customerId, List.of(createItemGroupDto));

        ItemGroupDto itemGroupDto = new ItemGroupDto(itemId, amount, LocalDate.now().plusDays(7), 62.5);
        OrderDto expected = new OrderDto(UUID.randomUUID(), customerId, List.of(itemGroupDto));

        //when
        OrderDto actual = orderController.placeOrder(createOrderDto);

        //then
        assertThat(actual).isEqualTo(expected);
    }


    @Autowired
    private WebTestClient webTestClient2;


    @Test
    void placeOrder_whenOrderIsNotValid_returnStatus400() {
        WebTestClient.ResponseSpec response = webTestClient2.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();
        response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void placeOrder_whenOrderIsValid_returnStatus201() {
        //add a customer
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(
                "John",
                "Doe",
                "hello@gmail.com",
                "Main street",
                10,
                "Metropolis",
                1000,
                "100");
        WebTestClient.ResponseSpec response1 = webTestClient2.post()
                .uri("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createCustomerDto), CreateCustomerDto.class)
                .exchange();
        CustomerDto customerDto = response1
                .expectBody(CustomerDto.class)
                .returnResult()
                .getResponseBody();

        //add an item
        CreateItemDto createItemDto = new CreateItemDto(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        WebTestClient.ResponseSpec response2 = webTestClient2.post()
                .uri("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createItemDto), CreateItemDto.class)
                .exchange();
        ItemDto itemDto = response2
                .expectBody(ItemDto.class)
                .returnResult()
                .getResponseBody();

        //place an order
        CreateItemGroupDto groupDto = new CreateItemGroupDto(itemDto.getItemId(), 5);
        CreateOrderDto createOrderDto = new CreateOrderDto(customerDto.getCustomerId(), List.of(groupDto));

        WebTestClient.ResponseSpec response = webTestClient2.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createOrderDto), CreateOrderDto.class)
                .exchange();

        //then
        response.expectStatus().isEqualTo(HttpStatus.CREATED);

        OrderDto expectedOrderDto = new OrderDto(UUID.randomUUID(),
                customerDto.getCustomerId(),
                List.of(new ItemGroupDto(itemDto.getItemId(), 5, LocalDate.now().plusDays(1), 62.5)));
        response.expectBody(OrderDto.class).isEqualTo(expectedOrderDto);
    }

}