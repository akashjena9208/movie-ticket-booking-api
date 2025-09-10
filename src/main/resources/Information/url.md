# 🎬 Movie Booking API – Endpoints

Base URL:
```
http://localhost:8080/api/v1
```

---

## 🔑 Authentication & Users

### Register User
```http
POST /users/register
```
- **Access:** Public
- **Body:** `UserRegistrationRequest`
- **Response:** `UserResponse`

### Update User
```http
PUT /users/{email}
```
- **Access:** `USER`
- **Body:** `UserUpdationRequest`
- **Response:** `UserResponse`

### Delete User (Soft Delete)
```http
DELETE /users/{email}
```
- **Access:** `USER`
- **Response:** `UserResponse`

---

## 🎭 Theaters

### Add Theater
```http
POST /theaters?email={ownerEmail}
```
- **Access:** `THEATER_OWNER`
- **Body:** `TheaterRequest`
- **Response:** `TheaterResponse`

### Get Theater by ID
```http
GET /theaters/{theaterId}
```
- **Access:** Public
- **Response:** `TheaterResponse`

### Update Theater
```http
PUT /theaters/{theaterId}
```
- **Access:** `THEATER_OWNER`
- **Body:** `TheaterRequest`
- **Response:** `TheaterResponse`

---

## 🖥 Screens

### Add Screen
```http
POST /theaters/{theaterId}/screens
```
- **Access:** `THEATER_OWNER`
- **Body:** `ScreenRequest`
- **Response:** `ScreenResponse`

### Get Screen
```http
GET /theaters/{theaterId}/screens/{screenId}
```
- **Access:** Public
- **Response:** `ScreenResponse`

---

## 🎥 Shows

### Add Show
```http
POST /shows/theaters/{theaterId}/screens/{screenId}
```
- **Access:** `THEATER_OWNER`
- **Params:**
    - `movieId` (string)
    - `startTime` (long – epoch millis)
    - `zoneId` (string – e.g. Asia/Kolkata)
- **Response:** `ShowResponse`

### Fetch Shows by Movie
```http
GET /shows/movies/{movieId}?date=YYYY-MM-DD&page=1&size=10&screenType=2D&zoneId=Asia/Kolkata
```
- **Access:** Public
- **Headers:**
    - `X-City: <cityName>` (required)
- **Response:** `Page<TheaterShowProjection>`

---

## 🎟 Bookings

### Create Booking
```http
POST /bookings
```
- **Access:** `USER`
- **Body:** `BookingRequestDto`
- **Response:** `BookingResponseDto`

### Get Booking by ID
```http
GET /bookings/{id}
```
- **Access:** `USER`
- **Response:** `BookingResponseDto`

### Get User Bookings
```http
GET /bookings/user/{userId}
```
- **Access:** `USER`
- **Response:** `List<BookingResponseDto>`

### Cancel Booking
```http
PUT /bookings/{id}/cancel
```
- **Access:** `USER`
- **Response:** `BookingResponseDto`

---

## 💳 Payments

### Make Payment
```http
POST /payments
```
- **Access:** `USER`
- **Body:** `PaymentRequestDto`
- **Response:** `PaymentResponseDto`

### Get Payment by ID
```http
GET /payments/{id}
```
- **Access:** `USER`
- **Response:** `PaymentResponseDto`

### Get Payments for Booking
```http
GET /payments/booking/{bookingId}
```
- **Access:** `USER`
- **Response:** `List<PaymentResponseDto>`

---

## ⭐ Feedbacks

### Create Feedback for Movie
```http
POST /movies/{movieId}/feedbacks
```
- **Access:** `USER`
- **Body:** `FeedbackRequest`
- **Response:** `FeedbackResponse`  
