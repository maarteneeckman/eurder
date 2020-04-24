package com.switchfully.eurder.service.customer;

public class CreateCustomerDto {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String street;
    private final int houseNumber;
    private final String city;
    private final int postcode;
    private final String phoneNumber;

    public CreateCustomerDto(String firstName, String lastName, String email, String street, int houseNumber, String city, int postcode, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
