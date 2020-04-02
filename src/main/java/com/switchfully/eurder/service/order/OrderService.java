package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public OrderDto placeOrder(CreateOrderDto createOrderDto) {
        Order order = orderMapper.createOrderDtoToOrder(createOrderDto);
        return orderMapper.orderToOrderDto(order);
    }
}
