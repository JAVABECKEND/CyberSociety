package com.example.cybersociety.exception;

public class PortfolioServiceException extends RuntimeException{
    public PortfolioServiceException () {
    }

    public PortfolioServiceException (String message) {
        super(message);
    }

    public PortfolioServiceException (String message, Throwable cause) {
        super(message, cause);
    }

    public PortfolioServiceException (Throwable cause) {
        super(cause);
    }

    public PortfolioServiceException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
