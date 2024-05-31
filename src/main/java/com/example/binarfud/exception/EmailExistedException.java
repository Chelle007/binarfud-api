package com.example.binarfud.exception;

public class EmailExistedException extends IllegalArgumentException {
    public EmailExistedException(String message) {
        super(message);
    }
}
