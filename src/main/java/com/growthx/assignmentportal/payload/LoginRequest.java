package com.growthx.assignmentportal.payload;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * Represents the request payload for user login.
 *
 */

import lombok.Data;

/**
 * This class serves as a data transfer object (DTO) for user
 * login requests. It contains fields for the username and
 * password required for authentication.
 */
@Data
public class LoginRequest {
    private String username;

    private String password;
}
