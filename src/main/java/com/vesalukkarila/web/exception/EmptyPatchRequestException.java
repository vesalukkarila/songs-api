package com.vesalukkarila.web.exception;

/**
 * Exception thrown when an attempt is made to apply a patch request
 * with no fields provided for update.
 * This exception indicates that at least one field must be present
 * in the patch request to proceed with the operation.
 */
public class EmptyPatchRequestException extends RuntimeException {

    /**
     * Constructs a new EmptyPatchRequestException with a default message.
     */
    public EmptyPatchRequestException() {
        super("At least one field must be provided.");
    }
}
