package com.growthx.assignmentportal.service;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * Custom implementation of Spring Security's UserDetailsService.
 * It loads user details by username or ID for authentication purposes.
 */

import com.growthx.assignmentportal.entity.User;
import com.growthx.assignmentportal.entity.UserPrincipal;
import com.growthx.assignmentportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Custom UserDetailsService implementation for loading user-specific data.
 * This class is used by Spring Security during the authentication process.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by their username. Called during the authentication process.
     *
     * @param username The username of the user.
     * @return UserDetails object containing user-specific data.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Fetch user from the database
        Optional<User> userOpt = userRepository.findByUsername(username);

        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Return a UserPrincipal object (used by Spring Security)
        return UserPrincipal.create(user);
    }

    /**
     * Loads a user by their ID. This method can be used in JWT-based authentication.
     *
     * @param userId The ID of the user.
     * @return UserDetails object containing user-specific data.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public UserDetails loadUserById(String userId){
        // Fetch user by ID from the database
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + userId));

        // Return a UserPrincipal object
        return UserPrincipal.create(user);
    }
}
