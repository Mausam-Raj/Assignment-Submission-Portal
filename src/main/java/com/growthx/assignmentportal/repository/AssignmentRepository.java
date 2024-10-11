package com.growthx.assignmentportal.repository;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * Repository interface for managing Assignment entities in the MongoDB database.
 */

import com.growthx.assignmentportal.entity.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Assignment entity. It provides methods to interact
 * with the 'assignments' collection in MongoDB.
 * Extends MongoRepository to inherit common CRUD operations.
 */
@Repository
public interface AssignmentRepository extends MongoRepository<Assignment, String> {

    /**
     * Finds a list of assignments based on the admin's ID.
     *
     * @param adminId The ID of the admin.
     * @return A list of assignments reviewed by the specified admin.
     */
    List<Assignment> findByAdminId(String adminId);
}
