package com.switchfully.eurder.domain.exceptions;

public class ItemNotUniqueException extends RuntimeException{
    public ItemNotUniqueException(String message){
        super(message);
    }
}
