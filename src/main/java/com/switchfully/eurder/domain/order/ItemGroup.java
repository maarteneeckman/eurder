package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.Item;

import java.time.LocalDate;

public class ItemGroup {

    private final Item item;
    private final int amount;
    private final LocalDate shippingDate;

    public ItemGroup(Item item, int amount, LocalDate shippingDate) {
        this.item = item;
        this.amount = amount;
        this.shippingDate = shippingDate;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }
}
