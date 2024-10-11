package com.growthx.assignmentportal.exception;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 */


public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
