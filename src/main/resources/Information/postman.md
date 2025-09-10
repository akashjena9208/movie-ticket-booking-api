# 🎬 Movie Booking API Documentation

This file contains **all API endpoints** for the Movie Booking System (Spring Boot + JWT Security).  
You can copy/paste these examples directly into Postman.

---

## 🔑 Authentication & Security

### Base URL
```
http://localhost:8080/api
```

### Headers
Every request (except register & login) requires a JWT token:

```
Authorization: Bearer <your_access_token>
Content-Type: application/json
```

---

## 👤 User & Auth APIs

### 1. Register User
**POST** `/auth/register`

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "StrongPass@123",
  "phoneNumber": "9876543210",
  "userRole": "USER",
  "dateOfBirth": "1995-05-10"
}
```

### 2. Login
**POST** `/auth/login`

```json
{
  "email": "john@example.com",
  "password": "StrongPass@123"
}
```

✅ Response:
```json
{
  "userId": "uuid",
  "username": "john_doe",
  "email": "john@example.com",
  "role": "USER",
  "accessToken": "xxxx",
  "refreshToken": "yyyy",
  "accessExpiration": 3600,
  "refreshExpiration": 86400
}
```

### 3. Refresh Token
**POST** `/auth/refresh`

```json
{
  "refreshToken": "your_refresh_token"
}
```

---

## 🎭 Movie APIs

### 1. Create Movie (Admin / TheaterOwner)
**POST** `/movies`

```json
{
  "title": "Inception",
  "description": "Mind-bending thriller",
  "castList": ["Leonardo DiCaprio", "Tom Hardy"],
  "runtime": "PT2H28M",
  "certificate": "UA",
  "genre": "SCIENCE_FICTION"
}
```

### 2. Get All Movies
**GET** `/movies`

### 3. Get Movie by ID
**GET** `/movies/{movieId}`

---

## 🎭 Theater APIs

### 1. Create Theater (TheaterOwner)
**POST** `/theaters`

```json
{
  "name": "PVR Cinemas",
  "address": "MG Road",
  "city": "Bangalore",
  "landmark": "Near Metro"
}
```

### 2. Get All Theaters
**GET** `/theaters`

### 3. Get Theater by ID
**GET** `/theaters/{theaterId}`

---

## 🎥 Screen APIs

### 1. Create Screen
**POST** `/screens`

```json
{
  "screenType": "IMAX",
  "capacity": 120,
  "noOfRows": 12
}
```

### 2. Get Screens by Theater
**GET** `/theaters/{theaterId}/screens`

---

## ⏱ Show APIs

### 1. Create Show
**POST** `/shows`

```json
{
  "startsAt": "2025-09-10T15:00:00Z",
  "endsAt": "2025-09-10T18:00:00Z",
  "screenId": "uuid",
  "movieId": "uuid",
  "theaterId": "uuid"
}
```

### 2. Get Shows for Movie
**GET** `/movies/{movieId}/shows`

---

## 🎟 Booking APIs

### 1. Create Booking
**POST** `/bookings`

```json
{
  "userId": "uuid",
  "showId": "uuid",
  "seatIds": ["s1", "s2", "s3"]
}
```

### 2. Get Booking by ID
**GET** `/bookings/{bookingId}`

### 3. Cancel Booking
**PATCH** `/bookings/{bookingId}/cancel`

---

## 💳 Payment APIs

### 1. Make Payment
**POST** `/payments`

```json
{
  "bookingId": "uuid",
  "amount": 500.0,
  "currency": "INR",
  "paymentMethod": "UPI"
}
```

### 2. Get Payment by ID
**GET** `/payments/{paymentId}`

---

## ⭐ Feedback APIs

### 1. Add Feedback
**POST** `/feedbacks`

```json
{
  "rating": 5,
  "review": "Amazing experience!"
}
```

### 2. Get Feedback for Movie
**GET** `/movies/{movieId}/feedbacks`

---

## 📚 Enum Reference

- **BookingStatus**: `PENDING`, `CONFIRMED`, `CANCELLED`, `REFUNDED`
- **Certificate**: `A`, `UA`, `U`
- **Genre**: `ACTION`, `COMEDY`, `DRAMA`, `HORROR`, `ROMANCE`, `SCIENCE_FICTION`, `THRILLER`, `ANIMATION`, `DOCUMENTARY`
- **PaymentMethod**: `CARD`, `UPI`, `NETBANKING`, `WALLET`
- **PaymentStatus**: `PENDING`, `SUCCESS`, `FAILED`
- **ScreenType**: `IMAX`, `TWO_D`, `THREE_D`
- **UserRole**: `USER`, `THEATER_OWNER`

---

## 🚀 Quick Test Flow

1. Register a user → `/auth/register`
2. Login → `/auth/login` (get tokens)
3. Create Theater → `/theaters`
4. Add Screen → `/screens`
5. Add Movie → `/movies`
6. Add Show → `/shows`
7. Book Tickets → `/bookings`
8. Make Payment → `/payments`
9. Leave Feedback → `/feedbacks`

---

✅ Now your API is **Postman-ready** with this one file!
