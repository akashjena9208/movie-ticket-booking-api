# Movie Ticket Booking System - Project Explanation

## 1. Project Title + Domain

**Project Title:** Movie Ticket Booking System\
**Domain:** Entertainment / Online Ticketing Platform

This is a production-ready backend system that allows users to book
movie tickets online while enabling theater owners to manage theaters,
screens, and shows.

------------------------------------------------------------------------

## 2. Problem Statement

In traditional movie booking systems, managing theaters, scheduling
shows, handling seat availability, processing payments, and ensuring
secure authentication can be complex.

This project solves:

-   Secure user authentication with JWT
-   Role-based access control (USER & THEATER_OWNER)
-   Automated seat generation per screen
-   Real-time seat booking with transactional safety
-   Payment processing with booking status updates
-   Email confirmation after successful booking

The system ensures data consistency, security, and scalability.

------------------------------------------------------------------------

## 3. Technologies Used

### Backend

-   Java 17
-   Spring Boot
-   Spring Security
-   Spring Data JPA
-   Hibernate
-   MySQL

### Security

-   JWT (Access + Refresh Token)
-   Role-Based Authorization
-   BCrypt Password Encoding

### Other Tools

-   Maven
-   Swagger (API Documentation)
-   Postman (API Testing)
-   Java Mail Sender (Email Notification)

------------------------------------------------------------------------

## 4. My Role

I designed and developed the complete backend architecture.

My responsibilities included:

-   Designing database schema and entity relationships
-   Implementing JWT-based authentication with refresh token rotation
-   Implementing role-based endpoint restriction
-   Building booking and payment transaction flow
-   Creating automatic seat generation logic
-   Handling concurrency and transaction management
-   Implementing global exception handling
-   Integrating email notification system

I built everything from scratch including security layer and business
logic.

------------------------------------------------------------------------

## 5. Key Features

### 1. Secure Authentication System

-   Access Token (short-lived)
-   Refresh Token (HTTP-only cookie)
-   Token rotation on refresh
-   Logout invalidates refresh token

### 2. Automated Seat Generation

-   Seats are automatically generated based on:
    -   Screen capacity
    -   Number of rows
-   Example: 5 rows × 10 seats = A1--E10

### 3. Transactional Booking System

-   Booking and seat locking handled inside database transaction
-   Prevents double booking
-   Payment updates booking status

### 4. Email Confirmation

-   After successful payment:
    -   Booking details are sent to user email
    -   Includes show details and seat numbers

------------------------------------------------------------------------

## 6. Challenges & Learning

### 1. Handling Double Booking

I solved seat collision issues using transactional logic and proper
database constraints.

### 2. Refresh Token Security

Implemented refresh token hashing and rotation to prevent replay
attacks.

### 3. Role-Based Restriction

Designed clean separation between USER and THEATER_OWNER endpoints.

### 4. Exception Handling

Implemented global exception handling using @ControllerAdvice.

------------------------------------------------------------------------

# Final Summary

This project demonstrates:

-   Secure authentication architecture
-   Clean layered architecture
-   Real-world transaction handling
-   Role-based access control
-   Production-level backend development

It simulates a real-world scalable movie booking backend system.
