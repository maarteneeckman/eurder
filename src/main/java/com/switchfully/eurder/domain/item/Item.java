package com.switchfully.eurder.domain.item;

public class Item {

    private final String name;
    private final String description;
    private double price;
    private int amountInStock;

    public Item(String name, String description, double price, int amountInStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
    }

}