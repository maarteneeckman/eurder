package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.Order;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public OrderDto placeOrder(CreateOrderDto createOrderDto) {
        validateOrder(createOrderDto);
        Order order = orderMapper.createOrderDtoToOrder(createOrderDto);
        return orderMapper.orderToOrderDto(order);
    }

    private void validateOrder(CreateOrderDto createOrderDto) {
        if (createOrderDto.getCustomerId() == null) {
            throw new IllegalArgumentException("CustomerId is not valid.");
        }
        if (createOrderDto.getCreateItemGroupDtos().isEmpty()) {
            throw new IllegalArgumentException("No items specified. Please order at least 1 item");
        }
        Collection<CreateItemGroupDto> groupDtos = createOrderDto.getCreateItemGroupDtos();
        for (CreateItemGroupDto dto : groupDtos) {
            if (dto.getItemId() == null) {
                throw new IllegalArgumentException("ItemId is not valid");
            }
            if (dto.getAmount() <= 0) {
                throw new IllegalArgumentException("Ordered quantity is not valid");
            }
        }
    }
}
