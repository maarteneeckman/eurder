package com.switchfully.eurder.domain.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderRepository {

    List<Order> orderList;

    public OrderRepository() {
        this.orderList = new ArrayList<>();
    }


    public void addOrder(Order order) {
        orderList.add(order);
    }

    public List<Order> getAllOrders() {
        return orderList;
    }
}
