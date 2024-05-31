package com.example.binarfud.exception;

public class UsernameExistedException extends IllegalArgumentException {
    public UsernameExistedException(String message) {
        super(message);
    }
}
