# 👤 Theater Module – Movie Booking System

The **Theater module** manages all theaters in the Movie Booking System.
It supports **theater creation, fetching details, and updating theaters** by **theater owners**.

---

## 🔹 What Theaters Can Do

### 1. Add Theater

Theater owners can add a new theater with:

* **Name** (max 20 characters)
* **Address** (max 50 characters)
* **City** (max 20 characters)
* **Landmark** (max 20 characters)

> Once added, the system stores theater details **linked to the theater owner**.

### 2. Update Theater

Theater owners can update:

* **Name**
* **Address**
* **City**
* **Landmark**

> Updates overwrite existing fields but maintain the theater ID.

### 3. Find Theater

Fetch details of a theater using:

* **Theater ID**

> Returns theater details including ID, name, address, city, and landmark.

---

## 🔹 Who Can Access

* **THEATER\_OWNER** → Can add, update, and fetch theaters.
* **USER** → Currently cannot add or update theaters.

---

## 🔹 Why This is Important

* **Data Integrity:** Prevents theaters without owners.
* **Security:** Only theater owners can manage theaters.
* **Flexibility:** Supports updates and retrieval by ID.

---

## ✅ Summary

The **Theater Module** handles **theater creation, updates, and retrieval** while maintaining **owner association and data integrity**.

---

# 🛠 API Endpoints

**Base URL:**

```
http://localhost:8080
```

**Common Header for all APIs:**

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

---

## 1️⃣ Add Theater

**POST** `/theaters?email={theaterOwnerEmail}`

**Purpose:**
Add a new theater under a theater owner.

**Headers:**

```
Content-Type: application/json
```

**Request Body Example:**

```json
{
  "name": "Galaxy Cinema",
  "address": "123 Main Street",
  "city": "Bhubaneswar",
  "landmark": "Near Park"
}
```

**Example URL:**

```
http://localhost:8080/theaters?email=rohit.owner@example.com
```

**Response Example:**

```json
{
  "message": "Theater has been succesfull created",
  "data": {
    "theaterId": "3b3c457b-8b18-41ac-aced-6ed0d132f374",
    "name": "Galaxy Cinema",
    "address": "123 Main Street",
    "city": "Bhubaneswar",
    "landmark": "Near Park"
  },
  "statusCode": 200
}
```

---

## 2️⃣ Find Theater

**GET** `/theaters/{theaterId}`

**Purpose:**
Retrieve theater details by ID.

**Headers:**

```
Content-Type: application/json
```

**Example URL:**

```
http://localhost:8080/theaters/3b3c457b-8b18-41ac-aced-6ed0d132f374
```

**Response Example:**

```json
{
  "message": "Theater has been successfully fetched",
  "data": {
    "theaterId": "3b3c457b-8b18-41ac-aced-6ed0d132f374",
    "name": "Galaxy Cinema",
    "address": "123 Main Street",
    "city": "Bhubaneswar",
    "landmark": "Near Park"
  },
  "statusCode": 200
}
```

---

## 3️⃣ Update Theater

**PUT** `/theaters/{theaterId}`

**Purpose:**
Update existing theater details.

**Headers:**

```
Content-Type: application/json
```

**Request Body Example:**

```json
{
  "name": "Galaxy Cinema Upd",
  "address": "456 New Street",
  "city": "Bhubaneswar",
  "landmark": "Near Mall"
}
```

**Example URL:**

```
http://localhost:8080/theaters/3b3c457b-8b18-41ac-aced-6ed0d132f374
```

**Response Example:**

```json
{
  "message": "Theater has been successfully Updated",
  "data": {
    "theaterId": "3b3c457b-8b18-41ac-aced-6ed0d132f374",
    "name": "Galaxy Cinema Upd",
    "address": "456 New Street",
    "city": "Bhubaneswar",
    "landmark": "Near Mall"
  },
  "statusCode": 200
}
```

---

## ✅ Validation Rules

| Field    | Required | Min | Max | Notes           |
| -------- | -------- | --- | --- | --------------- |
| name     | Yes      | 1   | 20  | Theater name    |
| address  | Yes      | 1   | 50  | Street address  |
| city     | Yes      | 1   | 20  | City name       |
| landmark | Yes      | 1   | 20  | Nearby landmark |

> Ensure all fields respect these constraints, otherwise **Spring Validation** will reject the request.

---

## ✅ Quick Postman Testing Flow

1. **Register Theater Owner** → use `/register` endpoint.
2. **Add Theater** → `/theaters?email={ownerEmail}` → save `theaterId`.
3. **Find Theater** → `/theaters/{theaterId}` → verify saved data.
4. **Update Theater** → `/theaters/{theaterId}` → update fields → verify response.
