package com.growthx.assignmentportal.config;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * This class configures the Spring Security setup for the application.
 * It manages authentication and authorization using JWT tokens, sets up a password encoder,
 * and defines security rules for different endpoints. It also specifies stateless session management
 * since the authentication is handled using tokens.
 */

import com.growthx.assignmentportal.security.JwtAuthenticationFilter;
import com.growthx.assignmentportal.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Bean that returns an instance of JwtAuthenticationFilter,
     * which intercepts requests and validates JWT tokens for authentication.
     *
     * @return JwtAuthenticationFilter - the filter used for JWT authentication
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    /**
     * Bean that returns a PasswordEncoder implementation.
     * BCryptPasswordEncoder is used to encode passwords for security.
     *
     * @return PasswordEncoder - the BCryptPasswordEncoder implementation
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean that provides an AuthenticationManager, responsible for managing authentication processes.
     * It is automatically configured by Spring to integrate with the CustomUserDetailsService.
     *
     * @param authenticationConfiguration - configuration for the authentication manager
     * @return AuthenticationManager - the authentication manager for handling authentication requests
     * @throws Exception if an error occurs during the process
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean that provides a DaoAuthenticationProvider.
     * It connects Spring Security with the CustomUserDetailsService for retrieving user information,
     * and the PasswordEncoder for verifying passwords.
     *
     * @return DaoAuthenticationProvider - provider responsible for handling authentication
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Security filter chain configuration that sets up HTTP security for the application.
     * It disables CSRF (since tokens are used for security), configures stateless session management,
     * and defines authorization rules for different endpoints.
     * Public endpoints like user and admin registration/login are open to everyone,
     * while other requests require authentication.
     *
     * @param http - the HttpSecurity object used for configuration
     * @return SecurityFilterChain - the security filter chain for the application
     * @throws Exception if an error occurs during the setup
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                    .csrf(customizer -> customizer.disable()) // Disable CSRF protection for stateless APIs
                    .authorizeHttpRequests(request -> request
                            .requestMatchers("/user/register", "/user/login", "/admin/register", "/admin/login")
                            .permitAll() // Publicly accessible endpoints
                            .anyRequest().authenticated() // All other endpoints require authentication
                    )
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management for JWT
                    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)// Add JWT filter
                    .httpBasic(Customizer.withDefaults())// Enable basic HTTP security
                    .build();
    }
}
