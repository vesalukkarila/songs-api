package com.vesalukkarila.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for the application that handles various
 * exceptions and returns appropriate HTTP responses.
 * This class uses Spring's @RestControllerAdvice annotation to
 * handle exceptions thrown by controllers and to return standardized error
 * responses in JSON format.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles SongNotFoundException and returns a 404 Not Found response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSongNotFound(SongNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles SongAlreadyExistsException and returns a 409 Conflict response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(SongAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleSongAlreadyExists(SongAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handles InvalidUUIDException and returns a 400 Bad Request response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(InvalidUUIDException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUUID(InvalidUUIDException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles EmptyPatchRequestException and returns a 400 Bad Request response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(EmptyPatchRequestException.class)
    public ResponseEntity<Map<String, String>> handleEmptyPatchRequest(EmptyPatchRequestException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException for validation errors
     * during method argument binding and returns a 400 Bad Request response
     * with detailed field errors.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing a map of field errors and HTTP status    //TODO: check after changes
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation error(s) in your request");

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String field = error.getField();
            String errorMessage =  error.getDefaultMessage();

            if ("publishYear".equals(field)) {
                field = "publish_year";
            }

            errors.put(field, errorMessage);
        }
        response.put("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
