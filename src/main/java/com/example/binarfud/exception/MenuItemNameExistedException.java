package com.example.binarfud.exception;

public class MenuItemNameExistedException extends IllegalArgumentException {
    public MenuItemNameExistedException(String message) {
        super(message);
    }
}
