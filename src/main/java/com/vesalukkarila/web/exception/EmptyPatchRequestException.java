package com.vesalukkarila.web.exception;

public class EmptyPatchRequestException extends RuntimeException {
    public EmptyPatchRequestException(){
        super("At least one field must be provided.");
    }
}
