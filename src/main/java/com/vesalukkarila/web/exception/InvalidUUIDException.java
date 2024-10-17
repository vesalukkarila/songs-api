package com.vesalukkarila.web.exception;

/**
 * Exception thrown when an invalid UUID is provided.
 * This indicates that the given identifier does not conform
 * to the expected UUID format.
 */
public class InvalidUUIDException extends RuntimeException {
    /**
     * Constructs a new InvalidUUIDException with a specified invalid UUID string.
     *
     * @param uuidStr the invalid UUID string that caused the exception
     */
    public InvalidUUIDException(String uuidStr) {
        super("Given identifier (" + uuidStr + ") is invalid. Expected format: 8-4-4-4-12 hex.");
    }
}
