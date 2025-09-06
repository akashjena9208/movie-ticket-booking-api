# 🎬 Show Module – Movie Booking System

The **Show module** manages movie show timings inside a screen of a theater. Each show is linked to a **movie**, a **screen**, and has a **start time** and **end time**.

---

## 🔹 What Shows Can Do

1. **Add Show** – assign a movie to a screen at a specific start time and time zone.
2. **Fetch Shows by Movie** – users can check available show timings for a given movie.
3. **Validate Overlaps** – prevents creating multiple shows at the same time in the same screen.

---

## 🔹 Who Can Access

* **THEATER_OWNER** → can add shows to their screens.
* **USER (Customer)** → can view all available shows for a selected movie.

---

## ✅ Why This is Important

* **Scheduling** – allows theaters to manage daily movie schedules.
* **Conflict Prevention** – ensures no two shows overlap in the same screen.
* **User Experience** – users can filter by city, date, or screen type to find suitable shows.

---

# 🛠 Show API Endpoints

**Base URL:** `http://localhost:8080`

**Headers:**

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

---

### 1️⃣ Add Show

**POST** `/theaters/{theaterId}/screens/{screenId}/shows`

**Request Params:**

| Param     | Required | Description                          |
| --------- | -------- | ------------------------------------ |
| movieId   | Yes      | The movie UUID                      |
| startTime | Yes      | Start time in epoch milliseconds     |
| zoneId    | Yes      | Zone ID (e.g., UTC, Asia/Kolkata)    |

**Request Example:**  

POST http://localhost:8080/theaters/3b3c457b-8b18-41ac-aced-6ed0d132f374/screens/baa0f2a7-ae78-41ae-9198-1cceb7d432bf/shows?movieId=1493d021-193a-47d4-8051-9343f1070842&startTime=1757164200000&zoneId=Asia/Kolkata



**Response Example:**

```json
{
  "message": "Show successfully created",
  "data": {
    "showId": "fcf229c2-3a92-43d0-bdeb-f4dcb7b4006f",
    "startsAt": "2025-09-06T10:30:00Z",
    "endsAt": "2025-09-06T13:00:00Z",
    "screenId": "baa0f2a7-ae78-41ae-9198-1cceb7d432bf",
    "screenType": "IMAX"
  },
  "statusCode": 200
}
2️⃣ Fetch Shows by Movie

GET /movies/{movieId}/shows

Headers:

Key	Value
X-City	Optional

Query Params Example:

http://localhost:8080/movies/1493d021-193a-47d4-8051-9343f1070842/shows?date=2025-09-06&page=0&size=5&zoneId=Asia/Kolkata


Response Example:

{
  "message": "Fetched Successfully",
  "data": [
    {
      "theaterName": "PVR Koramangala",
      "screenType": "IMAX",
      "startsAt": "2025-09-06T10:30:00Z",
      "price": 250
    },
    {
      "theaterName": "INOX Mall",
      "screenType": "3D",
      "startsAt": "2025-09-06T14:00:00Z",
      "price": 300
    }
  ],
  "statusCode": 200
}

✅ Error Handling
Scenario	Error Code	Message
Overlapping show in same screen	400	"Another Show is already booked"
Invalid movieId/screenId/theaterId	404	"Not Found"
No shows available	404	"No shows found"
Missing params	400	"Bad Request"

📌 Summary
The Show Module ensures that theaters can manage and schedule shows without conflicts, while users can easily browse available movie shows filtered by date, city, and screen type.


---

# ✅ Step 2: Postman Collection JSON

Save this as `ShowModule.postman_collection.json` and **import into Postman**.

```json
{
  "info": {
    "name": "🎬 Show Module – Movie Booking",
    "_postman_id": "abcd1234-5678-90ef-ghij-klmnopqrstuv",
    "description": "Postman collection for Show Module APIs (Add Show & Fetch Shows)",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Add Show",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/theaters/3b3c457b-8b18-41ac-aced-6ed0d132f374/screens/baa0f2a7-ae78-41ae-9198-1cceb7d432bf/shows?movieId=1493d021-193a-47d4-8051-9343f1070842&startTime=1757164200000&zoneId=Asia/Kolkata",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "theaters",
            "3b3c457b-8b18-41ac-aced-6ed0d132f374",
            "screens",
            "baa0f2a7-ae78-41ae-9198-1cceb7d432bf",
            "shows"
          ],
          "query": [
            { "key": "movieId", "value": "1493d021-193a-47d4-8051-9343f1070842" },
            { "key": "startTime", "value": "1757164200000" },
            { "key": "zoneId", "value": "Asia/Kolkata" }
          ]
        }
      }
    },
    {
      "name": "Fetch Shows by Movie",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          },
          {
            "key": "X-City",
            "value": "Bangalore"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/movies/1493d021-193a-47d4-8051-9343f1070842/shows?date=2025-09-06&page=0&size=5&zoneId=Asia/Kolkata",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8080",
          "path": [
            "movies",
            "1493d021-193a-47d4-8051-9343f1070842",
            "shows"
          ],
          "query": [
            { "key": "date", "value": "2025-09-06" },
            { "key": "page", "value": "0" },
            { "key": "size", "value": "5" },
            { "key": "zoneId", "value": "Asia/Kolkata" }
          ]
        }
      }
    }
  ]
}
