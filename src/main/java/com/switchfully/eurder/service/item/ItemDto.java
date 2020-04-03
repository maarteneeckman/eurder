package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.Item;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.UUID;

public class ItemDto {
    private final UUID itemId;
    private final String name;
    private final String description;
    private final double price;
    private final int amountInStock;

    public ItemDto(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.amountInStock = item.getAmountInStock();
    }

    public ItemDto(UUID itemId, String name, String description, double price, int amountInStock) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return Double.compare(itemDto.price, price) == 0 &&
                amountInStock == itemDto.amountInStock &&
                Objects.equals(name, itemDto.name) &&
                Objects.equals(description, itemDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, amountInStock);
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amountInStock=" + amountInStock +
                '}';
    }
}
