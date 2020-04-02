package com.switchfully.eurder.service.order;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OrderDto {

    private final UUID orderId;
    private final UUID customerId;
    private final List<ItemGroupDto> itemGroupDtos;

    public OrderDto(UUID orderId, UUID customerId, List<ItemGroupDto> itemGroupDtos) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemGroupDtos = itemGroupDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(customerId, orderDto.customerId) &&
                Objects.equals(itemGroupDtos, orderDto.itemGroupDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, itemGroupDtos);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", itemGroupDtos=" + itemGroupDtos +
                '}';
    }
}
