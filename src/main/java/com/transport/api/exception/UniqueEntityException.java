package com.transport.api.exception;

public class UniqueEntityException extends RuntimeException {
    public UniqueEntityException(String message) {
        super(message);
    }
}
