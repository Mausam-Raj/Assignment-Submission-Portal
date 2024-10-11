package com.growthx.assignmentportal.payload;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * Represents the request payload for uploading an assignment.
 *
 */

import lombok.Data;

/**
 * This class acts as a data transfer object (DTO) for receiving
 * assignment upload requests. It encapsulates the task details
 * and the associated admin's ID.
 */
@Data
public class AssignmentRequest {

    private String task;

    private String adminId;
}
