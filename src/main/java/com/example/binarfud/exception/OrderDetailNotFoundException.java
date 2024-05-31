package com.example.binarfud.exception;

public class OrderDetailNotFoundException extends IllegalArgumentException {
    public OrderDetailNotFoundException(String message) {
        super(message);
    }
}