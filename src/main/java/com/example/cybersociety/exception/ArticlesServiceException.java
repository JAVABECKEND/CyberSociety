package com.example.cybersociety.exception;

public class ArticlesServiceException extends RuntimeException{

    public ArticlesServiceException () {
    }

    public ArticlesServiceException (String message) {
        super (message);
    }

    public ArticlesServiceException (String message, Throwable cause) {
        super (message, cause);
    }

    public ArticlesServiceException (Throwable cause) {
        super (cause);
    }

    public ArticlesServiceException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super (message, cause, enableSuppression, writableStackTrace);
    }
}
