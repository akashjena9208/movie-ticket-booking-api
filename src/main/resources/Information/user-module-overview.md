# 👤 User Module – Movie Booking System

The **User module** manages all types of users in the Movie Booking System.  
It supports **customers (USER)** and **theater owners (THEATER_OWNER)**.

---

## 🔹 What Users Can Do

### 1. Register (Sign Up)
Users can create an account with:

- **Username**
- **Email** (unique)
- **Password** (secure: 8–20 chars, uppercase, lowercase, number, special char)
- **Phone Number** (10-digit Indian number)
- **Date of Birth**
- **Role** (USER / THEATER_OWNER)

> Once registered, the system stores details **securely**.

### 2. Update Profile
Users can update:

- **Username**
- **Email** (if not taken by another user)
- **Phone Number**
- **Date of Birth**

> Keeps user information **up-to-date**.

### 3. Soft Delete Account
Instead of permanent deletion:

- User cannot **log in** or use the system
- Record is **kept in the database** for audit/history

> Safer than hard delete — **data is not lost**.

---

## 🔹 Types of Users

- **USER (Customer)** → browse movies, book tickets
- **THEATER_OWNER** → manage theaters, movies, and shows

---

## 🔹 Why This is Important

- **Security:** Validates emails & passwords
- **Data Integrity:** Prevents duplicate emails/phones
- **Flexibility:** Supports multiple roles
- **Safety:** Soft delete keeps data for history

---

## ✅ Summary

The **User Module** handles **sign up, profile updates, and account deletion** while keeping the system **secure, flexible, and well-structured**.

---

# 🛠 API Endpoints

**Base URL:** `http://localhost:8080`

**Common Header for all APIs:**

| Key           | Value             |
|---------------|-----------------|
| Content-Type  | application/json |

---

## 1️⃣ Register User

**POST** `/register`

**Request Body:**
```json
{
  "username": "akash_jena",
  "email": "akash@gmail.com",
  "password": "Pass@1234",
  "phoneNumber": "9876543210",
  "userRole": "USER",
  "dateOfBirth": "1995-05-10"
}

http://localhost:8080/users/akash@example.com
{
  "username": "AkashNew",
  "email": "akash.new@example.com",
  "phoneNumber": "9123456789",
  "dateOfBirth": "2000-08-15"
}

http://localhost:8080/users/akash.new@example.com
