# 🎟 Booking Module – Movie Booking System

The **Booking module** manages user reservations for a specific **show** and selected **seats**.

---

## 🔹 What Booking Can Do

1. **Create Booking** – user books seats for a movie show.
2. **Fetch Booking** – get details of a booking by ID.
3. **Fetch User Bookings** – list all bookings for a given user.
4. **Cancel Booking** – cancel an existing booking (status → `CANCELLED`).

---

## 🔹 Who Can Access

* **USER (Customer)** → can create, fetch, and cancel their own bookings.
* **ADMIN** → can view and manage all bookings.

---

## ✅ Why This is Important

* **Seat Management** – ensures only valid seats are booked.
* **Payment Integration** – links bookings to payments.
* **User Experience** – allows customers to view and manage their bookings.

---

# 🛠 Booking API Endpoints

**Base URL:** `http://localhost:8080/api/bookings`

**Headers:**

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

---

### 1️⃣ Create Booking

**POST** `/api/bookings`

**Request Example:**

```json
{
  "userId": "12345",
  "showId": "67890",
  "seatIds": ["A1", "A2"]
}
```
Response Example:

{
"bookingId": "b123",
"bookingStatus": "PENDING",
"totalAmount": 500.0,
"userId": "12345",
"showId": "67890",
"seatIds": ["A1", "A2"],
"createdAt": "2025-09-06T10:30:00Z",
"updatedAt": "2025-09-06T10:30:00Z"
}

2️⃣ Get Booking by ID

GET /api/bookings/{id}

3️⃣ Get User Bookings

GET /api/bookings/user/{userId}

4️⃣ Cancel Booking

PUT /api/bookings/{id}/cancel

✅ Error Handling (Booking)
Scenario	Error Code	Message
Invalid booking ID	404	"Booking not found"
User or Show not found	404	"User/Show not found"
One or more seats not found	400	"Invalid seat selection"
Seats already booked	409	"Seats are already reserved"
💳 Payment Module – Movie Booking System

The Payment module handles transactions for bookings and updates booking status upon success.

🔹 What Payments Can Do

Make Payment – link to a booking, specify amount, currency, and method.

Fetch Payment by ID – get details of a transaction.

Fetch Payments for Booking – view all payments linked to a booking.

🔹 Who Can Access

USER (Customer) → make payments for their own bookings.

ADMIN → view and manage all transactions.

✅ Why This is Important

Ensures Secure Transactions – links booking with payment.

Updates Booking Status – booking confirmed only after payment success.

Supports Multiple Methods – UPI, CARD, NETBANKING, etc.

🛠 Payment API Endpoints

Base URL: http://localhost:8080/api/payments

Headers:

Key	Value
Content-Type	application/json
1️⃣ Make Payment

POST /api/payments

Request Example:

{
"bookingId": "b123",
"amount": 500.0,
"currency": "INR",
"paymentMethod": "UPI"
}


Response Example:

{
"paymentId": "p987",
"amount": 500.0,
"currency": "INR",
"paymentMethod": "UPI",
"status": "SUCCESS",
"bookingId": "b123",
"transactionId": "txn_abc123xyz",
"createdAt": "2025-09-06T10:32:00Z",
"updatedAt": "2025-09-06T10:32:00Z"
}

2️⃣ Get Payment by ID

GET /api/payments/{id}

3️⃣ Get Payments for a Booking

GET /api/payments/booking/{bookingId}

✅ Error Handling (Payments)
Scenario	Error Code	Message
Invalid booking ID	404	"Booking not found"
Payment not found	404	"Payment not found"
Insufficient details	400	"Invalid payment request"
Payment gateway failure	502	"Payment could not be processed"

📌 Summary
The Booking Module ensures users can select seats and reserve them securely.
The Payment Module guarantees that only paid bookings are confirmed, ensuring integrity and reliability in the system.


---

Do you want me to also prepare the **Postman Collection JSON file
