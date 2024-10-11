package com.growthx.assignmentportal.exception;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 */

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a global exception handler that handles validation and general exceptions
 * across the application. It intercepts exceptions thrown by controllers and returns a
 * user-friendly response to the client. The class uses @ControllerAdvice to allow centralized
 * exception handling.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method handles exceptions of type MethodArgumentNotValidException,
     * which are thrown when validation on an argument fails.
     *
     * It retrieves the field name and error message from each validation error and
     * returns a map of field names to error messages in the response body.
     *
     * @param ex - the MethodArgumentNotValidException thrown by the validation process
     * @return ResponseEntity with the validation errors and a BAD_REQUEST status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract the field names and corresponding error messages from the validation exception
        ex.getBindingResult().getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    // Add to the error map
                    errors.put(fieldName, errorMessage);
                });

        // Return the error details in the response body with a 400 Bad Request status
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AssignmentNotFoundException.class)
    public ResponseEntity<?> handleAssignmentNotFoundException(AssignmentNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidAdminIdException.class)
    public ResponseEntity<?> handleInvalidAdminIdException(InvalidAdminIdException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * This method handles all other general exceptions.
     * If an exception that is not specifically handled elsewhere is thrown,
     * this method will catch it and return a generic error message in the response.
     *
     * @param ex - the Exception thrown
     * @return ResponseEntity with the exception message and a BAD_REQUEST status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        // Return the exception message as the response body with a 400 Bad Request status
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
