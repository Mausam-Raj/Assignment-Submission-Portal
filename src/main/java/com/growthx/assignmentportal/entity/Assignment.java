package com.growthx.assignmentportal.entity;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * This class represents the Assignment entity for the assignment submission portal.
 * It maps to the 'assignments' collection in the MongoDB database.
 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents an assignment submitted by a user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "assignments")
public class Assignment {
    @Id
    private String id;

    private String userId;

    private String username;

    private String task;

    private String adminId;

    private String adminName;

    private Status status;

    private LocalDateTime timeStamp;

    /**
     * Enumeration representing the status of the assignment.
     */
    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
