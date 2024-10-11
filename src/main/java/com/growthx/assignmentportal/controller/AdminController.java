package com.growthx.assignmentportal.controller;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 */


import com.growthx.assignmentportal.entity.Assignment;
import com.growthx.assignmentportal.entity.User;
import com.growthx.assignmentportal.entity.UserPrincipal;
import com.growthx.assignmentportal.payload.LoginRequest;
import com.growthx.assignmentportal.security.JwtAuthenticationResponse;
import com.growthx.assignmentportal.service.AssignmentService;
import com.growthx.assignmentportal.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller manages the operations that an admin can perform, such as
 * registering, logging in, and managing assignments. It handles API requests
 * related to admin functionalities and is secured with role-based access control.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AssignmentService assignmentService;

    /**
     * Registers a new admin user. This endpoint requires a valid user object in the
     * request body. The user role is set as 'ADMIN' before saving.
     *
     * @param user The admin's user details
     * @return Success message or error message upon failure
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody User user) {
        try {
            user.setRole(User.Role.ADMIN);
            authService.registerUser(user);
            return ResponseEntity.ok("Admin registered successfully");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Authenticates an admin by validating their login credentials.
     *
     * @param loginRequest Contains username and password
     * @return JWT token if authentication succeeds, or an error message otherwise
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Fetches all assignments submitted by users for the authenticated admin.
     *
     * @return List of assignments
     */
    @GetMapping("/assignments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAssignments() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String adminId = principal.getId();
        List<Assignment> assignments = assignmentService.getAssignmentsForAdmin(adminId);
        return ResponseEntity.ok(assignments);
    }

    /**
     * Accepts a user's assignment submission.
     *
     * @param id The ID of the assignment
     * @return The accepted assignment or an error message
     */
    @PostMapping("/assignments/{id}/accept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> acceptAssignment(@PathVariable String id) {
        try {
            Assignment assignment = assignmentService.acceptAssignment(id);
            return ResponseEntity.ok(assignment);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Rejects a user's assignment submission.
     *
     * @param id The ID of the assignment
     * @return The rejected assignment or an error message
     */
    @PostMapping("/assignments/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectAssignment(@PathVariable String id) {
        try {
            Assignment assignment = assignmentService.rejectAssignment(id);
            return ResponseEntity.ok(assignment);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
