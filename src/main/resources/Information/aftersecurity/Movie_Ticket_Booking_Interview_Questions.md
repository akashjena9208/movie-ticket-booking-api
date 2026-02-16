# 🎬 Movie Ticket Booking System -- Interview Questions & Answers

## 🔐 Authentication & Security

### 1. What is JWT and how does it work?

JWT (JSON Web Token) is a stateless authentication mechanism. It
contains Header, Payload, and Signature. After login, server generates
token and client sends it in Authorization header.

### 2. What is Refresh Token?

Refresh token is used to generate a new access token when it expires. It
is stored securely (usually in DB).

### 3. What is Rotating Refresh Token?

On every refresh: - Old refresh token is invalidated. - New refresh
token is generated. This prevents replay attacks.

### 4. Difference between JWT and Session-based authentication?

JWT → Stateless, scalable, no server memory storage. Session → Server
stores session in memory/database.

### 5. How do you restrict APIs based on roles?

Using Spring Security: - @PreAuthorize - hasAuthority("USER") -
hasAuthority("THEATER_OWNER")

------------------------------------------------------------------------

## 🎭 Theater Owner Flow

### 6. How does owner create theater?

POST /theaters

### 7. How are screens created?

POST /theaters/{theaterId}/screens

### 8. How are seats generated automatically?

Seats are created dynamically based on: - capacity - number of rows

Seats per row = capacity / rows\
Row naming = A, B, C...\
Seat naming = A1, A2...

### 9. How are shows scheduled?

POST /shows/theaters/{theaterId}/screens/{screenId}/shows

------------------------------------------------------------------------

## 🎟 User Flow

### 10. How does user fetch shows?

GET /shows/movies/{movieId}/shows\
Requires header: X-City

### 11. How does booking work?

User selects seatIds → Booking created with status PENDING.

### 12. How do you prevent double booking?

-   Transaction management
-   Unique constraint (show_id + seat_id)
-   Seat locking strategy

### 13. How does payment work?

POST /payments\
On success → Booking confirmed.

------------------------------------------------------------------------

## 💳 Payment System

### 14. How is payment status managed?

PaymentStatus enum (PENDING, SUCCESS, FAILED)

### 15. What happens if payment fails?

Booking remains PENDING or marked FAILED.

------------------------------------------------------------------------

## 📧 Email Integration

### 16. How to send email after booking success?

-   Use Spring Boot MailSender
-   Trigger after payment SUCCESS
-   Send booking details

------------------------------------------------------------------------

## 🧠 Database & Transactions

### 17. How do transactions work internally?

Using @Transactional: - If exception → rollback - If success → commit

### 18. What is optimistic locking?

Uses version column. Prevents concurrent modification.

### 19. What is context path error?

Occurs if duplicate base path like: /api/v1/api/v1/...

------------------------------------------------------------------------

## 🏗 Architecture & Design

### 20. Why use DTO?

-   Hide entity
-   Control API response
-   Security

### 21. Why stateless architecture?

Better scalability for microservices.

### 22. How would you scale this system?

-   Redis for caching
-   Distributed locking
-   Message queue for emails
-   Load balancer

------------------------------------------------------------------------

# 🎯 Final Interview Summary

Your project demonstrates: - Secure JWT Authentication - Role-based
Access Control - Dynamic Seat Generation - Transaction-safe Booking -
Payment Integration - Email Notification - Clean Architecture
(Controller → Service → Repository) - Exception Handling -
Production-ready backend design
