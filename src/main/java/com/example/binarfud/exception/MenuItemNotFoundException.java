package com.example.binarfud.exception;

public class MenuItemNotFoundException extends IllegalArgumentException {
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
