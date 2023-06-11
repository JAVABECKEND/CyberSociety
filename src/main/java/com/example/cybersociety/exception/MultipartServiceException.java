package com.example.cybersociety.exception;

public class MultipartServiceException extends RuntimeException{
    public MultipartServiceException() {
    }

    public MultipartServiceException(String message) {
        super(message);
    }

    public MultipartServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipartServiceException(Throwable cause) {
        super(cause);
    }

    public MultipartServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
