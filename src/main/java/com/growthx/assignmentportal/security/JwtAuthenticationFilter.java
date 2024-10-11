package com.growthx.assignmentportal.security;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * This filter is responsible for intercepting requests and performing JWT token-based authentication.
 * It checks for the presence of a JWT token in the request header, validates the token, and loads
 * the associated user's details to set up security context for the request.
 */

import com.growthx.assignmentportal.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * This method filters every request to validate JWT token and sets the authenticated
     * user into the security context if the token is valid.
     *
     * @param request - the incoming HTTP request
     * @param response - the outgoing HTTP response
     * @param filterChain - the filter chain to pass control to the next filter
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Extract JWT from the request header
            String jwt = getJwtFromRequest(request);

            // Validate the token and authenticate the user
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
                String userid = tokenProvider.getUserIdFromJWT(jwt);

                // Load user details based on the user ID extracted from the token
                UserDetails userDetails = customUserDetailsService.loadUserById(userid);

                // Set authentication details in the Security Context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authenticated user in the Security Context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e) {
            System.out.println("JWT authetication failed");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            e.printStackTrace();
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Retrieves the JWT token from the request header, if it exists.
     *
     * @param request - the incoming HTTP request
     * @return the JWT token as a String, or null if no token is present
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
