package com.example.restbackend.customException;

public class CustomException extends RuntimeException {
    public CustomException(String exception) {
        super(exception);
    }
}