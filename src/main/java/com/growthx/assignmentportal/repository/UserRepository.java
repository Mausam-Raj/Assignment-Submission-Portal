package com.growthx.assignmentportal.repository;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * Repository interface for managing User entities in the MongoDB database.
 */

import com.growthx.assignmentportal.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity. It provides methods to interact
 * with the 'users' collection in MongoDB.
 * Extends MongoRepository to inherit common CRUD operations.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the User, if found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the given username already exists.
     *
     * @param username The username to check.
     * @return true if a user with the given username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Finds all users that have a specific role (USER or ADMIN).
     *
     * @param role The role to filter users by.
     * @return A list of users with the specified role.
     */
    List<User> findByRole(User.Role role);
}
