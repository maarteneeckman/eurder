package com.switchfully.eurder.domain.customer;

public class Address {
    private final String street;
    private final int number;
    private final String city;
    private final int postcode;

    public Address(String street, int number, String city, int postcode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public int getPostcode() {
        return postcode;
    }
}
