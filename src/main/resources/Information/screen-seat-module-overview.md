# 🎥 Screen Module – Movie Booking System

The **Screen module** manages screens inside a theater. Each screen has a **type**, a **capacity**, a **number of rows**, and a list of **seats** generated automatically.

---

## 🔹 What Screens Can Do

1. **Add Screen** – link a screen to a theater, define its type, capacity, and number of rows.
2. **Auto-Generate Seats** – based on capacity and rows, seats are created row-wise (A1, A2, … B1, B2 …).
3. **Fetch Screen** – get a specific screen with its seats.

---

## 🔹 Who Can Access

* **THEATER_OWNER** → add screens to their theaters.
* **USER (Customer)** → can only **view** screens & seats when booking.

---

## ✅ Why This is Important

* **Automation** – seats are auto-created (saves manual effort).
* **Consistency** – rows and capacity validated properly.
* **Scalability** – supports multiple screen types (e.g., IMAX, 3D, 2D).
* **Integrity** – linked with a specific theater.

---

# 🛠 Screen API Endpoints

**Base URL:** `http://localhost:8080`

**Headers:**

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

---

### 1️⃣ Add Screen

**POST** `/theaters/{theaterId}/screens`

**Request Example:**

```json
{
  "screenType": "IMAX",
  "capacity": 12,
  "noOfRows": 3
}
```

**Validation Rules:**

| Field       | Required | Rule                         |
| ----------- | -------- | ---------------------------- |
| screenType  | Yes      | Must be a valid enum (2D, 3D, IMAX, etc.) |
| capacity    | Yes      | Min: 1                       |
| noOfRows    | Yes      | Min: 1, must not exceed capacity |

**Response Example:**

```json
{
  "message": "Screen has been successfully created",
  "data": {
    "screenId": "baa0f2a7-ae78-41ae-9198-1cceb7d432bf",
    "screenType": "IMAX",
    "capacity": 12,
    "noOfRows": 3,
    "seats": [
      { "seatId": "fc0071b1-fa5e-4001-ad2e-a6972ac3f441", "name": "A1" },
      { "seatId": "530f2b57-1d7a-4e3e-af36-4a91f27b78d0", "name": "A2" },
      ...
      { "seatId": "6ac0d665-44e6-409e-80c9-aca27aeeef26", "name": "C4" }
    ]
  },
  "statusCode": 200
}
```

---

### 2️⃣ Fetch Screen by ID

**GET** `/theaters/{theaterId}/screens/{screenId}`

**Response Example:**

```json
{
  "message": "Screen has been successfully fetched",
  "data": {
    "screenId": "baa0f2a7-ae78-41ae-9198-1cceb7d432bf",
    "screenType": "IMAX",
    "capacity": 12,
    "noOfRows": 3,
    "seats": [
      { "seatId": "fc0071b1-fa5e-4001-ad2e-a6972ac3f441", "name": "A1" },
      { "seatId": "530f2b57-1d7a-4e3e-af36-4a91f27b78d0", "name": "A2" },
      ...
      { "seatId": "6ac0d665-44e6-409e-80c9-aca27aeeef26", "name": "C4" }
    ]
  },
  "statusCode": 200
}
```

---

## ✅ Seat Auto-Creation Logic

Example: `capacity = 12`, `noOfRows = 3`

- **Row A** → A1, A2, A3, A4
- **Row B** → B1, B2, B3, B4
- **Row C** → C1, C2, C3, C4

Stored in DB as:

```
[A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4]
```

---

## ✅ Error Handling

| Scenario                                  | Error Code | Message                          |
| ----------------------------------------- | ---------- | -------------------------------- |
| Invalid theater ID                        | 404        | "Theater not found by Id"        |
| Invalid screen ID                         | 404        | "Screen not found by Id"         |
| Rows > Capacity                           | 400        | "The no.of rows exceed capacity" |
| Missing required fields                   | 400        | Validation error message          |

---

## ✅ Quick Postman Testing Flow

1. Add a **theater** (from Theater Module).
2. Add a **screen** inside the theater (`/theaters/{theaterId}/screens`).
3. Fetch the screen by ID (`/theaters/{theaterId}/screens/{screenId}`).
4. Verify seats are auto-created correctly (A1, A2, B1, B2…).

---

📌 **Summary**  
The **Screen Module** ensures that each theater has well-structured screens with auto-generated seats. This helps users view and book seats seamlessly while allowing theater owners to manage screens efficiently.
