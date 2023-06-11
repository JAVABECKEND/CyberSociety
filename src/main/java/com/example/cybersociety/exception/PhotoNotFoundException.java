package com.example.cybersociety.exception;

public class PhotoNotFoundException extends RuntimeException{
    public PhotoNotFoundException(String message) {
        super(message);
    }
}
