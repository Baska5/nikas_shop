package com.exadel.nikas_shop.exception.exceptions;

public class InvalidStateTransitionException extends RuntimeException{

    public InvalidStateTransitionException() {
        super();
    }

    public InvalidStateTransitionException(String message) {
        super(message);
    }

    public InvalidStateTransitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
