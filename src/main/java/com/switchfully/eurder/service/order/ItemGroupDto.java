package com.switchfully.eurder.service.order;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ItemGroupDto {

    private final UUID itemId;
    private final int amount;
    private final LocalDate shippingDate;

    public ItemGroupDto(UUID itemId, int amount, LocalDate shippingDate) {
        this.itemId = itemId;
        this.amount = amount;
        this.shippingDate = shippingDate;
    }

    public UUID getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemGroupDto that = (ItemGroupDto) o;
        return amount == that.amount &&
                Objects.equals(shippingDate, that.shippingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, shippingDate);
    }
}
