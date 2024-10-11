package com.growthx.assignmentportal.entity;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * This class represents the User entity for the assignment submission portal.
 * It maps to the 'users' collection in the MongoDB database.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a user in the assignment portal system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private Role role;

    /**
     * Enumeration representing the role of a user.
     */
    public enum Role {
        USER,
        ADMIN
    }
}
