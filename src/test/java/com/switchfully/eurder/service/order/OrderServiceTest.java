package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Test
    void placeOrder_ifOrderIsValid_returnsOrderDto() {
        //given
        ItemRepository itemRepository = new ItemRepository();
        OrderMapper orderMapper = new OrderMapper(customerRepository, itemRepository);
        OrderService orderService = new OrderService(orderMapper);

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
        itemRepository.addItem(item);

        int amount = 5;

        CreateItemGroupDto createItemGroupDto = new CreateItemGroupDto(itemId, amount);
        CreateOrderDto createOrderDto = new CreateOrderDto(customerId, List.of(createItemGroupDto));

        //when
        OrderDto actual = orderService.placeOrder(createOrderDto);

        ItemGroupDto itemGroupDto = new ItemGroupDto(itemId, amount, LocalDate.now().plusDays(1), 62.5);
        OrderDto expected = new OrderDto(UUID.randomUUID(), customerId, List.of(itemGroupDto));

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void placeOrder_ifOrderIsNotValid_throwsException() {
        //given
        ItemRepository itemRepository = new ItemRepository();
        OrderMapper orderMapper = new OrderMapper(customerRepository, itemRepository);
        OrderService orderService = new OrderService(orderMapper);

        Item item = new Item(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        UUID itemId = item.getId();
        itemRepository.addItem(item);

        int amount = 0;

        CreateItemGroupDto createItemGroupDto = new CreateItemGroupDto(itemId, amount);
        CreateOrderDto createOrderDto = new CreateOrderDto(UUID.randomUUID(), List.of(createItemGroupDto));
        //the customer-id is not relevant, because the ordered quantity is checked before the customer is retrieved

        //then
        Assertions.assertThatThrownBy(() -> orderService.placeOrder(createOrderDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Ordered quantity is not valid");
    }

}