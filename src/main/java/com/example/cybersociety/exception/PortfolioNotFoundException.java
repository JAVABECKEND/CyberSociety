package com.example.cybersociety.exception;

public class PortfolioNotFoundException extends RuntimeException{
    public PortfolioNotFoundException (String message) {
        super(message);
    }

    public PortfolioNotFoundException (String message, Throwable cause) {
        super(message, cause);
    }

    public PortfolioNotFoundException (Throwable cause) {
        super(cause);
    }

    public PortfolioNotFoundException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
