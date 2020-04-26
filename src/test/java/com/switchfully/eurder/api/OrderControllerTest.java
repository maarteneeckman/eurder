package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ItemRepositoryNoDB;
import com.switchfully.eurder.service.customer.CreateCustomerDto;
import com.switchfully.eurder.service.customer.CustomerDto;
import com.switchfully.eurder.service.item.CreateItemDto;
import com.switchfully.eurder.service.item.ItemDto;
import com.switchfully.eurder.service.order.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class OrderControllerTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    class TestWithMockito {

        @Mock
        CustomerRepository customerRepository;

        @Mock
        ItemRepository itemRepository;

        @Test
        void placeOrder_returnsCorrectDto() {
            //given
//            ItemRepositoryNoDB itemRepository = new ItemRepositoryNoDB();
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
            when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customer));

            Item item = new Item(
                    "Pen",
                    "It writes underwater. It also writes other words.",
                    12.5,
                    20);
            UUID itemId = item.getId();
            when(itemRepository.findById(any(UUID.class))).thenReturn(Optional.of(item));

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
//            CustomerRepositoryNoDB customerRepository = new CustomerRepositoryNoDB();
//            ItemRepositoryNoDB itemRepository = new ItemRepositoryNoDB();
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
            when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customer));

            Item item = new Item(
                    "Pen",
                    "It writes underwater. It also writes other words.",
                    12.5,
                    0);
            UUID itemId = item.getId();
            when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

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

    }

    @Nested
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @DirtiesContext
    class TestWithSpringBoot {
        @Autowired
        private WebTestClient webTestClient;


        @Test
        void placeOrder_whenOrderIsNotValid_returnStatus400() {
            WebTestClient.ResponseSpec response = webTestClient.post()
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
            WebTestClient.ResponseSpec response1 = webTestClient.post()
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
            WebTestClient.ResponseSpec response2 = webTestClient.post()
                    .uri("/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(createItemDto), CreateItemDto.class)
                    .exchange();
            ItemDto itemDto = response2
                    .expectBody(ItemDto.class)
                    .returnResult()
                    .getResponseBody();

            Assertions.assertThat(itemDto.toString()).isNotNull();
            Assertions.assertThat(itemDto.getItemId()).isNotNull();

            //place an order
            CreateItemGroupDto groupDto = new CreateItemGroupDto(itemDto.getItemId(), 5);
            CreateOrderDto createOrderDto = new CreateOrderDto(customerDto.getCustomerId(), List.of(groupDto));

            Assertions.assertThat(createOrderDto.getCreateItemGroupDtos().get(0).getItemId()).isEqualByComparingTo(itemDto.getItemId());

            WebTestClient.ResponseSpec response = webTestClient.post()
                    .uri("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(createOrderDto), CreateOrderDto.class)
                    .exchange();

            //then
            response.expectStatus().isEqualTo(HttpStatus.CREATED);
//
//            OrderDto expectedOrderDto = new OrderDto(UUID.randomUUID(),
//                    customerDto.getCustomerId(),
//                    List.of(new ItemGroupDto(itemDto.getItemId(), 5, LocalDate.now().plusDays(1), 62.5)));
//            response.expectBody(OrderDto.class).isEqualTo(expectedOrderDto);
        }

    }

}