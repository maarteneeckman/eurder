package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.customer.Address;
import com.switchfully.eurder.domain.customer.Customer;

import java.util.UUID;

public class CustomerDto {

    private final UUID customerId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Address address;
    private final long phoneNumber;


    public CustomerDto(Customer customer) {
        customerId = customer.getCustomerId();
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        email = customer.getEmail();
        address = customer.getAddress();
        phoneNumber = customer.getPhoneNumber();
    }

    public UUID getCustomerId() {
        return customerId;
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

    public Address getAddress() {
        return address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }
}
