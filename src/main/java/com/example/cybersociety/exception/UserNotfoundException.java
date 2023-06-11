package com.example.cybersociety.exception;

public class UserNotfoundException extends RuntimeException{
    public UserNotfoundException() {
    }

    public UserNotfoundException(String message) {
        super(message);
    }

    public UserNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotfoundException(Throwable cause) {
        super(cause);
    }

    public UserNotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
