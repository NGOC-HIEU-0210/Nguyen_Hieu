package com.hieu.exception;

public class OperationNotPermittedException extends Exception {
    public OperationNotPermittedException(String message) {
        super(message);
    }
}
