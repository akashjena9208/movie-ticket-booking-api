# 🎟️ Movie Booking System -- Modules Overview

## 👤 User Module

The **User Module** manages registration, authentication, and profile
management for all users, including customers and theater owners.

### 🔹 Features

1.  **Register (Sign Up)**
    -   Fields:
        -   Username\
        -   Email (unique)\
        -   Password (8--20 chars, must contain uppercase, lowercase,
            number, special char)\
        -   Phone Number (10-digit Indian format)\
        -   Date of Birth\
        -   Role (USER / THEATER_OWNER)\
    -   System securely stores details.
2.  **Update Profile**
    -   Editable fields: Username, Email (if unique), Phone Number, Date
        of Birth
3.  **Soft Delete Account**
    -   Account disabled (cannot log in).\
    -   Data retained in the system (for history/audit).

### 🔹 Roles

-   **USER (Customer)** → browse movies, book tickets\
-   **THEATER_OWNER** → manage theaters, movies, and shows

### ✅ Why Important

-   **Security** → email/password validation\
-   **Data Integrity** → prevents duplicates\
-   **Flexibility** → supports multiple roles\
-   **Safety** → soft delete keeps history

------------------------------------------------------------------------

## 🎭 Theater Module

The **Theater Module** manages theater details linked to owners.

### 🔹 Features

1.  **Add Theater**
    -   Fields: Name (20), Address (50), City (20), Landmark (20)\
    -   Stored and linked to the theater owner
2.  **Update Theater**
    -   Editable fields: Name, Address, City, Landmark
3.  **Find Theater**
    -   Input: Theater ID\
    -   Output: ID, Name, Address, City, Landmark

### 🔹 Roles & Access

-   **THEATER_OWNER** → add/update/fetch theaters\
-   **USER** → view theaters (no modification rights)

### ✅ Why Important

-   **Data Integrity** → each theater must have an owner\
-   **Security** → restricted access\
-   **Flexibility** → allows updates and retrieval

------------------------------------------------------------------------

## 🎬 Show Module

The **Show Module** manages movie show schedules within theater screens.

### 🔹 Features

1.  **Add Show** → assign movie to a screen at specific time & time
    zone\
2.  **Fetch Shows by Movie** → users can view available timings\
3.  **Validate Overlaps** → ensures no duplicate shows in the same
    screen & time

### 🔹 Roles & Access

-   **THEATER_OWNER** → can add/manage shows\
-   **USER** → can view shows for selected movies

### ✅ Why Important

-   **Scheduling** → manage daily screenings\
-   **Conflict Prevention** → avoids overlapping shows\
-   **User Experience** → users filter by city, date, screen type

------------------------------------------------------------------------

## 🎥 Movie Module

The **Movie Module** manages movie details for the entire system.

### 🔹 Features

1.  **Add Movie** → create movie with title, description, runtime,
    certificate, genre, cast\
2.  **Update Movie** → modify existing movie details\
3.  **Fetch Movie by ID** → return movie details + average rating\
4.  **Search Movies** → by title (case-insensitive)

### 🔹 Roles & Access

-   **ADMIN / THEATER_OWNER** → add & update movies\
-   **USER** → view & search movies

### ✅ Why Important

-   **Centralized Data** → all theaters reference movie catalog\
-   **User Experience** → detailed info & search\
-   **Ratings Integration** → from feedbacks\
-   **Scalability** → supports multiple genres, certificates, casts

------------------------------------------------------------------------

## 💳 Payment Module

The **Payment Module** manages financial transactions linked to
bookings.

### 🔹 Features

1.  **Make Payment** → initiate transaction (amount, currency, method:
    UPI, CARD, NETBANKING)\
2.  **Fetch Payment by ID** → view transaction details\
3.  **Fetch Payments for a Booking** → list all linked payments

### 🔹 Roles & Access

-   **USER** → can pay for their own bookings\
-   **ADMIN** → can view/manage all transactions

### ✅ Why Important

-   **Security** → ensures safe transactions\
-   **Booking Confirmation** → only after successful payment\
-   **Tracking** → unique transaction IDs\
-   **Flexibility** → multiple payment methods

------------------------------------------------------------------------

⚡ **Final Summary**\
- **User Module** → authentication, roles, profile management\
- **Theater Module** → theater CRUD tied to owners\
- **Show Module** → schedules & prevents conflicts\
- **Movie Module** → centralized movie catalog with ratings\
- **Payment Module** → secure transactions tied to bookings
