package com.example.restbackend.customexception;

public class CustomException extends RuntimeException {
    public CustomException(String exception) {
        super(exception);
    }
}