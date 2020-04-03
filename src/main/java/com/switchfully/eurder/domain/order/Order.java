package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.customer.Customer;

import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID orderId;
    private final Customer customer;
    private final List<ItemGroup> itemGroups;


    public Order(Customer customer, List<ItemGroup> itemGroups) {
        orderId = UUID.randomUUID();
        this.customer = customer;
        this.itemGroups = itemGroups;
    }

    public UUID getId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public double getTotalPrice() {
        return itemGroups.stream()
                .mapToDouble(itemGroup -> itemGroup.getPrice())
                .sum();
    }
}
