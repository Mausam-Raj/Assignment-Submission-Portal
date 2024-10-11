package com.growthx.assignmentportal.controller;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 */

import com.growthx.assignmentportal.entity.Assignment;
import com.growthx.assignmentportal.entity.User;
import com.growthx.assignmentportal.entity.UserPrincipal;
import com.growthx.assignmentportal.exception.InvalidAdminIdException;
import com.growthx.assignmentportal.payload.AdminResponse;
import com.growthx.assignmentportal.payload.AssignmentRequest;
import com.growthx.assignmentportal.payload.LoginRequest;
import com.growthx.assignmentportal.repository.UserRepository;
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
import java.util.stream.Collectors;

/**
 * This controller manages user operations such as registering, logging in,
 * uploading assignments, and viewing admin details. The user can interact
 * with assignments and see available admins to submit assignments.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentService assignmentService;

    /**
     * Registers a new user with the 'USER' role.
     *
     * @param user The user's details
     * @return Success message or error message upon failure
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user){
        try{
            user.setRole(User.Role.USER);
            authService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Authenticates a user by validating their login credentials.
     *
     * @param loginRequest Contains username and password
     * @return JWT token if authentication succeeds, or an error message otherwise
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        try{
            String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Allows a user to upload an assignment. The assignment includes the user's task
     * and is associated with a specific admin.
     *
     * @param assignmentRequest Contains the task details and the admin ID
     * @return Success message or error message upon failure
     */
    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadAssignment(@Valid @RequestBody AssignmentRequest assignmentRequest) {
        try {
            UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = principal.getId();
            String userName = principal.getUsername();
            Assignment assignment = new Assignment();
            assignment.setUserId(userId);
            assignment.setUsername(userName);
            assignment.setTask(assignmentRequest.getTask());
            assignment.setAdminId(assignmentRequest.getAdminId());

            // Fetch admin details to validate the admin ID
            User admin = userRepository.findById(assignmentRequest.getAdminId()).orElse(null);
            if (admin == null || !admin.getRole().equals(User.Role.ADMIN)) {
                throw new InvalidAdminIdException("Invalid admin ID");
            }
            assignment.setAdminName(admin.getUsername());

            assignmentService.uploadAssignment(assignment);
            return ResponseEntity.ok("Assignment uploaded successfully");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Fetches the list of available admins to whom a user can upload assignments.
     *
     * @return List of admins (ID and username)
     */
    @GetMapping("/admins")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAdmins() {
        List<User> admins = userRepository.findByRole(User.Role.ADMIN);
        List<AdminResponse> adminResponses = admins.stream()
                .map(admin -> new AdminResponse(admin.getId(), admin.getUsername()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(adminResponses);
    }
}
