# Assignment-Submission-Portal`
This project is a `backend system` for an assignment submission portal where users can upload assignments, and admins can accept or reject these assignments. The project is built using `Java`, `Spring Boot 3.3.4`, `Spring Security 6.3.3`, `JWT` for authentication, `MongoDB` as the database, and Lombok for cleaner code.

## Table of Contents
- [Features](Features)
- [Technologies Used](Technologies-Used)
- [Prerequisites](Prerequisites)
- [Getting Started](Getting-Started)
- [Running the Application](Running-the-Application)
- [API Endpoints](API-Endpoints)
- [Database](Database)
- [Testing](Testing)
- [Conclusion](Conclusion)

## Features
- User Operations:
  - User registration and login.
  - Upload assignments tagged to a specific admin.

- Admin Operations:
  - Admin registration and login.
  - View assignments tagged to the admin.
  - Accept or reject assignments.

## Technologies Used
- `Java 17`
- `Spring Boot 3.3.4`
- `Spring Security 6.3.3`
- `JWT for Authentication`
- `MongoDB`
- `Lombok`

## Prerequisites
Before you begin, ensure you have the following installed on your system:
- `Java 17`
- `MongoDB` (locally or using MongoDB Atlas)
- `Postman` or any REST API client for testing the APIs

## Getting Started

1. ### **Clone the repository:**
  ```
    git clone https://github.com/yourusername/assignment-submission-portal.git
    cd assignment-submission-portal
  ```

2. ### **Set up MongoDB:**
  - If you're using MongoDB locally, ensure that MongoDB is running on its default port (27017).
  - If you're using MongoDB Atlas, update the MongoDB connection URL in application.properties or application.yml.

3. ### **Configure the Application:**
   
    Open the src/main/resources/application.properties file and update the MongoDB connection URL if needed:
  ```
    spring.data.mongodb.uri=mongodb://localhost:27017/assignment_db
  ```

4. ### **Build and run the application**
- Build the application using Maven:
  ```
    mvn clean install
  ```

- Run the application:
  ```
    mvn spring-boot:run
  ```

  The application will start at http://localhost:8080.

## Running the Application
The application uses JWT for authentication. After registering a user or admin, you will need to obtain a JWT token by logging in. This token must be included in the Authorization header (as a Bearer token) for protected endpoints.

1. **User Registration**
  To register a new user:
    ```
      POST http://localhost:8080/user/register
    ```
  Request Body (JSON):
    ```
      {
        "username": "john_doe",
        "password": "password123",
        "role": "USER"
      }
    ```

2. **User Login**
  To log in:
    ```
      POST http://localhost:8080/user/login
    ```
  Request Body (JSON):
    ```
      {
        "username": "john_doe",
        "password": "password123"
      }
    ```
  The response will include a JWT token, which you should use for subsequent requests in the Authorization header:
    ```
      {
        "token": "eyJhbGciOiJIUzI1NiJ9..."
      }
    ```

3. **User Upload Assignment**
  To upload an assignment:
    ```
      POST http://localhost:8080/user/upload
    
    ```
  Request Body (JSON):
    ```
      {
        "userId": "john_doe",
        "task": "Complete the Spring Boot project",
        "admin": "admin_alok"
      }
    ```
4. **User Get ALl Admins**
   To get all admins:
      ```
        GET http://localhost:8080/user/admins
      ```

5. **Admin Register**
  To register a new admin:
    ```
      POST http://localhost:8080/admin/register
    ```
  Request Body (JSON):
    ```
      {
        "username": "admin_alok",
        "password": "adminpass",
        "role": "ADMIN"
      }
    ```
6. **Admin Login**
  To log in as an admin:
    ```
      POST http://localhost:8080/admin/login
    ```
  Request Body (JSON):
    ```
      {
        "username": "admin_alok",
        "password": "adminpass"
      }
    ```

7. **Admin Assignments**
  To view assignments assigned to a specific admin:
    ```
      GET http://localhost:8080/admin/assignments
    ```

8. **Admin Assignment Accept**
  To accept an assignment:
    ```
      POST http://localhost:8080/admin/assignments/{id}/accept
    ```

9. **Admin Assignment Reject**
  To reject an assignment:
    ```
      POST http://localhost:8080/admin/assignments/{id}/reject
    ```

## API Endpoints
  ### User Endpoints

| Method | Endpoint  | Description           | Authorization  |
|--------|-----------|-----------------------|----------------|
| POST   | `/user/register` | Register a new user  | No             |
| POST   | `/user/login`    | Log in a user        | No             |
| POST   | `/user/upload`   | Upload an assignment | User           |
| GET    | `/user/admins`   | Get all admins       | User           |

### Admin Endpoints

| Method | Endpoint                        | Description                      | Authorization  |
|--------|----------------------------------|----------------------------------|----------------|
| POST   | `/admin/register`                | Register a new admin             | No             |
| POST   | `/admin/login`                   | Log in an admin                  | No             |
| GET    | `/admin/assignments`             | View assignments for the admin   | Admin          |
| POST   | `/admin/assignments/{id}/accept` | Accept an assignment             | Admin          |
| POST   | `/admin/assignments/{id}/reject` | Reject an assignment             | Admin          |





## Database
The application uses MongoDB for storing users, admins, and assignments. MongoDB is a NoSQL database and provides flexibility in handling dynamic data.

### MongoDB Collections:
  - users: Stores user and admin details, including role-based access control.
  - assignments: Stores all assignments uploaded by users, with each assignment tagged to a specific admin.

## Testing
  You can use Postman or any other REST client to test the API endpoints. Make sure to include the JWT token in the Authorization header for protected routes.


## Conclusion
  This project provides a fully functional backend system for an assignment submission portal with role-based access control, user management, and JWT-based authentication. The use of modern technologies like Spring Boot, MongoDB, and Spring Security ensures scalability, security, and flexibility.














