package com.switchfully.eurder.domain.exceptions;

public class CustomerNotUniqueException extends RuntimeException{
    public CustomerNotUniqueException(String message) {
        super(message);
    }
}
