package com.switchfully.eurder.service.order;

import java.util.List;
import java.util.UUID;

public class CreateOrderDto {


    private final UUID customerId;
    private final List<CreateItemGroupDto> createItemGroupDtos;

    public CreateOrderDto(UUID customerId, List<CreateItemGroupDto> itemGroupDtos) {
        this.customerId = customerId;
        this.createItemGroupDtos = itemGroupDtos;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<CreateItemGroupDto> getCreateItemGroupDtos() {
        return createItemGroupDtos;
    }
}
