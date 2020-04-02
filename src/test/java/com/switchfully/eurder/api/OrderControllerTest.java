package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.service.customer.CreateCustomerDto;
import com.switchfully.eurder.service.item.CreateItemDto;
import com.switchfully.eurder.service.order.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Test
    void placeOrder_returnsCorrectDto(){
        //given
        CustomerRepository customerRepository = new CustomerRepository();
        ItemRepository itemRepository = new ItemRepository();
        OrderMapper orderMapper = new OrderMapper(customerRepository, itemRepository);
        OrderService orderService = new OrderService(orderMapper);
        OrderController orderController = new OrderController(orderService);

        Customer customer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber(100)
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

        ItemGroupDto itemGroupDto = new ItemGroupDto(itemId, amount, LocalDate.now().plusDays(1));
        OrderDto expected = new OrderDto(UUID.randomUUID(), customerId, List.of(itemGroupDto));

        //when
        OrderDto actual = orderController.placeOrder(createOrderDto);

        //then
        assertThat(actual).isEqualTo(expected);
    }



    @Autowired
    private WebTestClient webTestClient;


    @Test
    void placeOrder_whenOrderIsNotValid_returnStatus400() {
        WebTestClient.ResponseSpec response = this.webTestClient.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();
        response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

//    @Test
//    void placeOrder_whenOrderIsValid_returnStatus201() {
//        //given
////        CustomerRepository customerRepository = new CustomerRepository();
////        ItemRepository itemRepository = new ItemRepository();
//        //OrderMapper orderMapper = new OrderMapper(customerRepository, itemRepository);
//        //OrderService orderService = new OrderService(orderMapper);
//        //OrderController orderController = new OrderController(orderService);
//
//        //add a customer
//        CreateCustomerDto createCustomerDto = new CreateCustomerDto(
//                "John",
//                "Doe",
//                "hello@gmail.com",
//                "Main street",
//                10,
//                "Metropolis",
//                1000,
//                100);
//        WebTestClient.ResponseSpec response1 = webTestClient.post()
//                .uri("/customers")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(createCustomerDto), CreateCustomerDto.class)
//                .exchange();
//
//        //add an item
//        CreateItemDto createItemDto = new CreateItemDto(
//                "Pen",
//                "It writes underwater. It also writes other words.",
//                12.5,
//                20);
//        WebTestClient.ResponseSpec response2 = webTestClient.post()
//                .uri("/items")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(createItemDto), CreateItemDto.class)
//                .exchange();
//
//        //place an order
//        UUID itemId = item.getId();
//        itemRepository.addItem(item);
//
//        int amount = 5;
//
//        CreateItemGroupDto createItemGroupDto = new CreateItemGroupDto(itemId, amount);
//        CreateOrderDto createOrderDto = new CreateOrderDto(customerId, List.of(createItemGroupDto));
//
//
//        WebTestClient.ResponseSpec response = webTestClient.post()
//                .uri("/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(createOrderDto), CreateOrderDto.class)
//                .exchange();
//        response.expectStatus().isEqualTo(HttpStatus.CREATED);
//    }

}