package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.service.order.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

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

}