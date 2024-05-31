package com.example.binarfud.exception;

public class DuplicateMenuItemException extends RuntimeException{
    public DuplicateMenuItemException(String message) {
        super(message);
    }
}
