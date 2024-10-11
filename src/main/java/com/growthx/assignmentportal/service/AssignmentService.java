package com.growthx.assignmentportal.service;

/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * Service class for handling business logic related to the Assignment entity.
 * It provides functionality to upload, accept, and reject assignments,
 * as well as retrieve assignments for a specific admin.
 */

import com.growthx.assignmentportal.entity.Assignment;
import com.growthx.assignmentportal.exception.AssignmentNotFoundException;
import com.growthx.assignmentportal.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Service class to handle assignment operations.
 * This class acts as an intermediary between the controller and the repository.
 */
@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    /**
     * Uploads an assignment, setting the timestamp and status to PENDING.
     *
     * @param assignment The assignment object to be uploaded.
     * @return The saved assignment.
     */
    public Assignment uploadAssignment(Assignment assignment){
        // Set the current time as timestamp
        assignment.setTimeStamp(LocalDateTime.now());

        // Set default status to PENDING
        assignment.setStatus(Assignment.Status.PENDING);

        // Save assignment in the database
        return assignmentRepository.save(assignment);
    }

    /**
     * Retrieves all assignments assigned to a specific admin.
     *
     * @param adminId The ID of the admin.
     * @return A list of assignments assigned to the specified admin.
     */
    public List<Assignment> getAssignmentsForAdmin(String adminId){

        // Retrieve assignments for a specific admin
        return assignmentRepository.findByAdminId(adminId);
    }

    /**
     * Marks an assignment as accepted by updating its status.
     *
     * @param assignmentId The ID of the assignment to accept.
     * @return The updated assignment object.
     * @throws Exception if the assignment is not found.
     */
    public Assignment acceptAssignment(String assignmentId) throws Exception {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found"));

        // Update the status to ACCEPTED
        assignment.setStatus(Assignment.Status.ACCEPTED);

        // Save the updated assignment
        return assignmentRepository.save(assignment);
    }

    /**
     * Marks an assignment as rejected by updating its status.
     *
     * @param assignmentId The ID of the assignment to reject.
     * @return The updated assignment object.
     * @throws Exception if the assignment is not found.
     */
    public Assignment rejectAssignment(String assignmentId) throws Exception {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found"));

        // Update the status to REJECTED
        assignment.setStatus(Assignment.Status.REJECTED);

        // Save the updated assignment
        return assignmentRepository.save(assignment);
    }
}
