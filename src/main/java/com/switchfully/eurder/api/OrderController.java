package com.switchfully.eurder.api;

import com.switchfully.eurder.service.order.CreateOrderDto;
import com.switchfully.eurder.service.order.OrderDto;
import com.switchfully.eurder.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@RequestBody CreateOrderDto createOrderDto){
        return orderService.placeOrder(createOrderDto);
    }
}
