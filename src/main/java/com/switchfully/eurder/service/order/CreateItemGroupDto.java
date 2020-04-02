package com.switchfully.eurder.service.order;

import java.util.UUID;

public class CreateItemGroupDto {

    private final UUID itemId;
    private final int amount;

    public CreateItemGroupDto(UUID itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public UUID getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}
