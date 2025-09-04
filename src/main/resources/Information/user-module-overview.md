# 👤 User & Theater Modules – Movie Booking System

This document includes both **User Module** and **Theater Module** for the Movie Booking System.

---

# 👤 User Module

The **User module** manages all types of users. It supports **customers (USER)** and **theater owners (THEATER\_OWNER)**.

## 🔹 What Users Can Do

### 1. Register (Sign Up)

* **Username**
* **Email** (unique)
* **Password** (secure: 8–20 chars, uppercase, lowercase, number, special char)
* **Phone Number** (10-digit Indian number)
* **Date of Birth**
* **Role** (USER / THEATER\_OWNER)

> System stores details **securely**.

### 2. Update Profile

* **Username**
* **Email** (if not taken)
* **Phone Number**
* **Date of Birth**

### 3. Soft Delete Account

* User cannot **log in**
* Record remains in the database

## 🔹 Types of Users

* **USER (Customer)** → browse movies, book tickets
* **THEATER\_OWNER** → manage theaters, movies, shows

## 🔹 Why This is Important

* **Security:** Validates emails & passwords
* **Data Integrity:** Prevents duplicates
* **Flexibility:** Supports multiple roles
* **Safety:** Soft delete keeps history

## ✅ Summary

Handles **sign up, updates, deletion** while keeping system secure and flexible.

---

# 🛠 User API Endpoints

**Base URL:** `http://localhost:8080`

**Headers:**

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

### 1️⃣ Register User

**POST** `/register`

**Request Examples:**

```json
// User
{
  "username": "akash_jena",
  "email": "akash@gmail.com",
  "password": "Pass@1234",
  "phoneNumber": "9876543210",
  "userRole": "USER",
  "dateOfBirth": "1995-05-10"
}

// Theater Owner
{
  "username": "RohitOwner",
  "email": "rohit.owner@example.com",
  "password": "Owner@1234",
  "phoneNumber": "9876543210",
  "userRole": "THEATER_OWNER",
  "dateOfBirth": "1990-01-15"
}
```

### 2️⃣ Update User

**PUT** `/users/{email}`

**Request Example:**

```json
{
  "username": "AkashNew",
  "email": "akash.new@example.com",
  "phoneNumber": "9123456789",
  "dateOfBirth": "2000-08-15"
}
```

---

# 👤 Theater Module

The **Theater module** manages theaters, linked to theater owners.

## 🔹 What Theaters Can Do

1. **Add Theater** – name, address, city, landmark
2. **Update Theater** – update existing details
3. **Find Theater** – fetch details by ID

## 🔹 Who Can Access

* **THEATER\_OWNER** → add, update, fetch theaters
* **USER** → cannot manage theaters

## ✅ Summary

Handles **theater creation, updates, retrieval** with owner association.

---

# 🛠 Theater API Endpoints

**Base URL:** `http://localhost:8080`

**Headers:**

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

### 1️⃣ Add Theater

**POST** `/theaters?email={theaterOwnerEmail}`

**Request Example:**

```json
{
  "name": "Galaxy Cinema",
  "address": "123 Main Street",
  "city": "Bhubaneswar",
  "landmark": "Near Park"
}
```

### 2️⃣ Find Theater

**GET** `/theaters/{theaterId}`

### 3️⃣ Update Theater

**PUT** `/theaters/{theaterId}`

**Request Example:**

```json
{
  "name": "Galaxy Cinema Upd",
  "address": "456 New Street",
  "city": "Bhubaneswar",
  "landmark": "Near Mall"
}
```

## ✅ Validation Rules

| Field    | Required | Min | Max |
| -------- | -------- | --- | --- |
| name     | Yes      | 1   | 20  |
| address  | Yes      | 1   | 50  |
| city     | Yes      | 1   | 20  |
| landmark | Yes      | 1   | 20  |

## ✅ Quick Postman Testing Flow

1. Register theater owner (`/register`)
2. Add theater (`/theaters?email={ownerEmail}`)
3. Find theater (`/theaters/{theaterId}`)
4. Update theater (`/theaters/{theaterId}`)
