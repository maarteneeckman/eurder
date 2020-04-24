package com.switchfully.eurder.domain.customer;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {
    @Column(name="street")
    private String street;
    @Column(name="housenumber")
    private int number;
    @Column(name="city")
    private String city;
    @Column(name="postcode")
    private int postcode;

    protected Address(){} //required for spring data

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return number == address.number &&
                postcode == address.postcode &&
                Objects.equals(street, address.street) &&
                Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, city, postcode);
    }

}
