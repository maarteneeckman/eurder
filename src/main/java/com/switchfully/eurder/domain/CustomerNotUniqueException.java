package com.switchfully.eurder.domain;

public class CustomerNotUniqueException extends RuntimeException{
    public CustomerNotUniqueException(String message) {
        super(message);
    }
}
