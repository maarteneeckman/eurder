package com.switchfully.eurder.service.item;

public class CreateItemDto {

    private final String name;
    private final String description;
    private final double price;
    private final int amountInStock;

    public CreateItemDto(String name, String description, double price, int amountInStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
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
}
