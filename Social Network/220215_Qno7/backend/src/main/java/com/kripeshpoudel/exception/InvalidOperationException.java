package com.kripeshpoudel.exception;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException() {
    }

    public InvalidOperationException(String message) {
        super(message);
    }
}
