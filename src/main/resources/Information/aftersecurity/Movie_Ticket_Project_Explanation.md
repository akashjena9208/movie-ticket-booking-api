# 🎬 Movie Ticket Booking System -- Project Explanation Guide

## 🟢 1. One-Line Summary

I built a production-ready Movie Ticket Booking REST API using Spring
Boot.\
It supports role-based authentication (USER & THEATER_OWNER), theater
management, show scheduling, seat booking with transaction handling,
payment processing, email confirmation, and JWT-based security with
refresh token rotation.

------------------------------------------------------------------------

## 🟢 2. Problem Statement

The goal of the project is to simulate a real-world movie ticket booking
platform like BookMyShow, where:

-   Theater Owners manage theaters and shows
-   Users book seats and make payments securely

------------------------------------------------------------------------

## 🟢 3. Architecture Overview

The project follows layered architecture:

-   **Controller Layer** → Handles HTTP requests
-   **Service Layer** → Business logic
-   **Repository Layer** → Database interaction (JPA)
-   **Security Layer** → JWT authentication & role-based authorization
-   **Global Exception Handling** → Structured error responses

------------------------------------------------------------------------

## 🟢 4. Authentication & Security

Authentication is implemented using JWT.

-   Access Token → Short-lived
-   Refresh Token → Stored in database
-   Refresh Token Rotation implemented
-   Password encrypted using BCrypt
-   Role-based access using USER and THEATER_OWNER
-   Method-level security using @PreAuthorize
-   Custom AuthenticationEntryPoint & AccessDeniedHandler

------------------------------------------------------------------------

## 🟢 5. Theater Owner Flow

1.  Register as THEATER_OWNER
2.  Login
3.  Create Theater
4.  Add Screens
5.  Seats auto-generated
6.  Add Movies
7.  Schedule Shows

### Seat Generation Logic

Seats are generated dynamically:

-   Rows → A, B, C, D, E
-   Seat numbers → 1 to 10
-   Example → A1, A2, A3...

------------------------------------------------------------------------

## 🟢 6. User Flow

1.  Register as USER
2.  Login
3.  View Movies
4.  Filter Shows by City & Date
5.  Select Seats manually
6.  Create Booking
7.  Make Payment
8.  Receive Email Confirmation
9.  Leave Feedback

------------------------------------------------------------------------

## 🟢 7. Booking Logic

Booking is handled inside a database transaction.

Steps: 1. Validate show exists 2. Check seats availability 3. Lock seats
4. Create booking with PENDING status 5. On successful payment → update
booking to CONFIRMED

@Transactional ensures rollback if any step fails.

------------------------------------------------------------------------

## 🟢 8. Payment System

Simulated UPI/Card payment system.

After successful payment: - Booking status updated - Payment record
saved - Confirmation email sent

------------------------------------------------------------------------

## 🟢 9. Email Integration

Integrated JavaMailSender to send booking confirmation emails after
successful payment.

------------------------------------------------------------------------

## 🟢 10. Database Tables

-   users
-   refresh_token
-   theaters
-   screens
-   seats
-   movies
-   shows
-   bookings
-   payments
-   feedback

------------------------------------------------------------------------

## 🟢 11. What This Project Demonstrates

-   Secure authentication design
-   Refresh token rotation
-   Transaction management
-   Role-based access control
-   REST API best practices
-   Production-level exception handling
-   Real-world backend architecture

------------------------------------------------------------------------

## 🎯 Closing Statement for Interview

Overall, this project demonstrates authentication, authorization,
transaction management, concurrency handling, layered architecture, and
real-world production-ready backend development using Spring Boot.
