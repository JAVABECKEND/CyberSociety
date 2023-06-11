package com.example.cybersociety.exception;

public class MediaNotFoundException extends RuntimeException{
    public MediaNotFoundException(String message) {
        super(message);
    }
}
