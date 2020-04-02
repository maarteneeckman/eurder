package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.Item;

import java.time.LocalDate;

public class ItemGroup {

    private final Item item;
    private final int amount;
    private final LocalDate shippingDate;
    private final double price;

    public ItemGroup(Item item, int amount, LocalDate shippingDate, double price) {
        this.item = item;
        this.amount = amount;
        this.shippingDate = shippingDate;
        this.price = price;
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

    public double getPrice() {
        return price;
    }
}
