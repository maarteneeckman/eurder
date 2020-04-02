package com.switchfully.eurder.api;

import com.switchfully.eurder.service.order.CreateOrderDto;
import com.switchfully.eurder.service.order.OrderDto;
import com.switchfully.eurder.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderDto placeOrder(CreateOrderDto createOrderDto){
        return orderService.placeOrder(createOrderDto);
    }
}
