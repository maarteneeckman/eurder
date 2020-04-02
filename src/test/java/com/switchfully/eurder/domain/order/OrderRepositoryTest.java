package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class OrderRepositoryTest {

    @Test
    void addOrder_addsOrderList(){
        //given
        OrderRepository orderRepository = new OrderRepository();
        Customer customer = Customer.CustomerBuilder.newCustomer()
                .withFirstName("John")
                .withLastName("Doe")
                .withAddress(new Address("Main street", 10, "Metropolis", 1000))
                .withPhoneNumber(100)
                .withEmail("hello@gmail.com")
                .build();
        Item item = new Item(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        int amount = 5;
        ItemGroup itemGroup = new ItemGroup(item, amount, LocalDate.now().plusDays(1), 62.5);
        Order order = new Order(customer, List.of(itemGroup));

        //when
        orderRepository.addOrder(order);
        //then
        Assertions.assertThat(orderRepository.getAllOrders().size()).isEqualTo(1);
    }


}