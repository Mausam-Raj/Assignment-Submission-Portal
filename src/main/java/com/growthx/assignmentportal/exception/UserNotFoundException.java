package com.growthx.assignmentportal.exception;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 */


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
