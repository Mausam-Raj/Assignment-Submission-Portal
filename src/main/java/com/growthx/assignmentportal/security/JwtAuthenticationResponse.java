package com.growthx.assignmentportal.security;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * This class is used to encapsulate the JWT token that will be returned
 * as a response upon successful authentication.
 * It includes the access token and token type.
 */

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    /**
     * Constructor to initialize the JwtAuthenticationResponse with the access token.
     *
     * @param accessToken - the JWT token as a String
     */
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
