package com.exadel.nikas_shop.exception.exceptions;

public class NonUniqueException extends RuntimeException {
    public NonUniqueException() {
        super();
    }

    public NonUniqueException(String message) {
        super(message);
    }

    public NonUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
