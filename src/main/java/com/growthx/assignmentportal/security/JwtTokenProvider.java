package com.growthx.assignmentportal.security;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * This class is responsible for generating and validating JWT tokens.
 * It provides methods to create JWT tokens for authenticated users,
 * extract user details from the token, and validate its authenticity.
 */

import com.growthx.assignmentportal.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Secret key for signing the JWT token
    @Value("${jwt.secret}")
    private String jwtSecret;

    // JWT expiration time in milliseconds
    @Value("${jwt.expiration-time}")
    private long jwtExpirationInMillis;

    private SecretKey key;

    /**
     * Generates a JWT token based on the user's information.
     *
     * @param user - the user object containing ID and role information
     * @return the JWT token as a String
     */
    public String generateToken(User user) {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMillis);

        // Build the JWT token with subject (user ID), claims (user role), and expiration time
        return Jwts.builder()
                .subject(user.getId())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token - the JWT token
     * @return the user ID as a String
     */
    public String getUserIdFromJWT(String token) {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Extracts the user role from a JWT token.
     *
     * @param token - the JWT token
     * @return the user role as a String
     */
    public String getUserRoleFromJWT(String token) {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Claims claims = Jwts.parser()
               .verifyWith(key)
               .build()
               .parseSignedClaims(token)
               .getPayload();
        return claims.get("role", String.class);
    }

    /**
     * Validates the JWT token.
     *
     * @param authToken - the JWT token
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String authToken){
        try {
            key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
