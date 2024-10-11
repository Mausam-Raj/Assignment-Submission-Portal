package com.growthx.assignmentportal.payload;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 *
 * Represents the response payload containing admin user information.
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class serves as a data transfer object (DTO) for sending
 * admin user information in the response payload. It includes
 * fields for the admin's ID and username.
 */
@Data
@AllArgsConstructor
public class AdminResponse {

    private String id;

    private String username;
}
