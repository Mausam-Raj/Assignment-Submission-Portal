package com.growthx.assignmentportal.service;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * Service class for managing user authentication and registration.
 * It provides methods to register users and authenticate them using JWT tokens.
 */

import com.growthx.assignmentportal.entity.User;
import com.growthx.assignmentportal.exception.UserAlreadyExistsException;
import com.growthx.assignmentportal.exception.UserNotFoundException;
import com.growthx.assignmentportal.repository.UserRepository;
import com.growthx.assignmentportal.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling authentication and registration logic.
 */
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Registers a new user by encoding their password and saving them to the database.
     *
     * @param user The user entity to be registered.
     * @return A success message.
     * @throws Exception if the username already exists.
     */
    public String registerUser (User user) throws Exception {
        // Check for unique username
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException("Username is already taken");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user in the database
        userRepository.save(user);
        return "User registered successfully";
    }

    /**
     * Authenticates a user by verifying their credentials and generating a JWT token.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The generated JWT token.
     * @throws Exception if authentication fails.
     */
    public String authenticateUser (String username, String password) throws Exception {

        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Retrieve user from the repository
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(!userOpt.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOpt.get();

        // Generate JWT token
        return tokenProvider.generateToken(user);
    }
}
