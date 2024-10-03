package com.vesalukkarila.web.exception;

public class InvalidUUIDException extends RuntimeException{
    public InvalidUUIDException(String uuidStr) {
        super("Given identifier: (" + uuidStr + ") is invalid. Expected format: 8-4-4-4-12 hex.");
    }
}
