# Movie Booking System API

A **Movie Booking System** built using **Spring Boot 3**, **Spring Security 6**, **JWT**, **MySQL**, and **Hibernate/JPA**.
This system allows users to register, login, view movies, book tickets, provide feedback, and manage theaters, screens, and shows.

---

## Base URL

```
http://localhost:8080/api/v1
```

Swagger UI (API documentation & testing):

```
http://localhost:8080/api/v1/swagger-ui.html
```

---

## Technologies Used

* Java 17
* Spring Boot 3
* Spring Security 6
* JWT (JSON Web Tokens)
* Hibernate / JPA
* MySQL 8
* Lombok
* Springdoc OpenAPI 3 (Swagger)
* Maven

---

## Database Configuration (`application.yml`)

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/******?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: *****
    password: *****

  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true

server:
  port: 8080
  servlet:
    context-path: /api/v1

app:
  token:
    secret: "MzY3NjQ3MTY0NzE2NDcxNjQ3MTY0NzE2NDcxNjQ3MTY0NzE2NDcxNjQ3MTY0NzE2NA=="
    accessDuration: 15   # in minutes
    refreshDuration: 60  # in minutes
```

---

## Entities Overview

| Entity       | Description                                      |
| ------------ | ------------------------------------------------ |
| UserDetails  | Base class for all users                         |
| User         | Regular user with bookings and feedback          |
| TheaterOwner | User who can manage theaters                     |
| Theater      | Contains screens and shows                       |
| Screen       | Contains seats and shows                         |
| Seat         | Seats inside a screen                            |
| Movie        | Movie details including cast, genre, certificate |
| Show         | Movie show timings in theaters/screens           |
| Booking      | User booking of seats in a show                  |
| Payment      | Booking payment details                          |
| Feedback     | User feedback for movies                         |

---

## Enums

* `BookingStatus`: PENDING, CONFIRMED, CANCELLED, REFUNDED
* `Certificate`: A, UA, U
* `Genre`: ACTION, COMEDY, DRAMA, HORROR, ROMANCE, SCIENCE\_FICTION, THRILLER, ANIMATION, DOCUMENTARY
* `PaymentMethod`: CARD, UPI, NETBANKING, WALLET
* `ScreenType`: IMAX, TWO\_D, THREE\_D
* `UserRole`: USER, THEATER\_OWNER
* `TokenType`: ACCESS, REFRESH

---

## Security

* JWT based authentication for `ACCESS` and `REFRESH` tokens.
* `USER` role can access booking, feedback, and profile APIs.
* `THEATER_OWNER` role can manage theaters, screens, and shows.

Endpoints `/register` and `/login` are public, all other endpoints require a valid `ACCESS` token.

---

## API Endpoints

### User APIs

* **Register User**

    * POST `/register`
    * Request:

      ```json
      {
        "username": "akash_jena",
        "email": "akash@gmail.com",
        "password": "Pass@1234",
        "phoneNumber": "9876543210",
        "userRole": "USER",
        "dateOfBirth": "1995-05-10"
      }
      ```

* **Login User**

    * POST `/login`
    * Request:

      ```json
      {
        "email": "akash@gmail.com",
        "password": "Pass@1234"
      }
      ```

* **Get User by ID**

    * GET `/users/{userId}`
    * Requires `Authorization: Bearer <ACCESS_TOKEN>`

* **Update User**

    * PUT `/users/{userId}`
    * Request:

      ```json
      {
        "username": "akash_jena",
        "email": "akash@gmail.com",
        "phoneNumber": "9876543210",
        "dateOfBirth": "1995-05-10"
      }
      ```

* **Delete User**

    * DELETE `/users/{userId}`

### Movie APIs

* **Create Movie** - POST `/movies`
* **Get Movie** - GET `/movies/{movieId}`
* **Update Movie** - PUT `/movies/{movieId}`
* **Delete Movie** - DELETE `/movies/{movieId}`
* **List Movies** - GET `/movies`

### Theater APIs

* **Create Theater** - POST `/theaters`
* **Get Theater** - GET `/theaters/{theaterId}`
* **Update Theater** - PUT `/theaters/{theaterId}`
* **Delete Theater** - DELETE `/theaters/{theaterId}`

### Screen APIs

* **Create Screen** - POST `/screens`
* **Get Screen** - GET `/screens/{screenId}`
* **Update Screen** - PUT `/screens/{screenId}`
* **Delete Screen** - DELETE `/screens/{screenId}`

### Seat APIs

* **Create Seat** - POST `/seats`
* **Get Seat** - GET `/seats/{seatId}`
* **Update Seat** - PUT `/seats/{seatId}`
* **Delete Seat** - DELETE `/seats/{seatId}`

### Show APIs

* **Create Show** - POST `/shows`
* **Get Show** - GET `/shows/{showId}`
* **Update Show** - PUT `/shows/{showId}`
* **Delete Show** - DELETE `/shows/{showId}`

### Booking APIs

* **Create Booking** - POST `/bookings`
* **Get Booking** - GET `/bookings/{bookingId}`
* **Update Booking Status** - PUT `/bookings/{bookingId}`
* **Cancel Booking** - DELETE `/bookings/{bookingId}`

### Payment APIs

* **Create Payment** - POST `/payments`
* **Get Payment** - GET `/payments/{paymentId}`

### Feedback APIs

* **Add Feedback** - POST `/feedbacks`
* **Get Feedback** - GET `/feedbacks/{feedbackId}`

---

## Notes

* Make sure to **register first**, then **login** to get the JWT `ACCESS_TOKEN`.
* Use the token in **Authorization header** for all protected routes:

```
Authorization: Bearer <ACCESS_TOKEN>
```

* Swagger UI helps to test all endpoints with JWT authentication.
* Ensure **database is running** and configured properly in `application.yml`.

---

## Example JSON Response

```json
{
    "message": "New User Details Has been added

```
