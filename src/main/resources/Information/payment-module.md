# 💳 Payment Module – Movie Booking System

The **Payment module** handles all payment-related workflows for movie bookings.  
It links each payment to a booking and updates the booking status once the payment succeeds.

---

## 🔹 What Payments Can Do

1. **Make Payment** – initiate a payment for a booking (amount, currency, method).
2. **Fetch Payment by ID** – retrieve a specific payment transaction.
3. **Fetch Payments for a Booking** – view all payments linked to a booking.

---

## 🔹 Who Can Access

* **USER (Customer)** → can make payments for their own bookings.
* **ADMIN** → can view and manage all transactions.

---

## ✅ Why This is Important

* Ensures **secure and validated transactions**.
* **Updates Booking Status** → confirmed only after payment success.
* Supports multiple payment methods (**UPI, CARD, NETBANKING**).
* Provides **transaction tracking** with unique IDs.

---

# 🛠 Payment API Endpoints

**Base URL:** `http://localhost:8080/api/payments`

**Headers:**

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

---

### 1️⃣ Make Payment

**POST** `/api/payments`

**Request Example:**

```json
{
  "bookingId": "b123",
  "amount": 750.0,
  "currency": "INR",
  "paymentMethod": "CARD"
}
```
Response Example:

{
"paymentId": "p987",
"amount": 750.0,
"currency": "INR",
"paymentMethod": "CARD",
"status": "SUCCESS",
"bookingId": "b123",
"transactionId": "txn_123456789",
"createdAt": "2025-09-06T11:00:00Z",
"updatedAt": "2025-09-06T11:00:00Z"
}

2️⃣ Get Payment by ID

GET /api/payments/{id}

Response Example:

{
"paymentId": "p987",
"amount": 750.0,
"currency": "INR",
"paymentMethod": "CARD",
"status": "SUCCESS",
"bookingId": "b123",
"transactionId": "txn_123456789",
"createdAt": "2025-09-06T11:00:00Z",
"updatedAt": "2025-09-06T11:00:00Z"
}

3️⃣ Get Payments for a Booking

GET /api/payments/booking/{bookingId}

Response Example:

[
{
"paymentId": "p987",
"amount": 750.0,
"currency": "INR",
"paymentMethod": "CARD",
"status": "SUCCESS",
"bookingId": "b123",
"transactionId": "txn_123456789",
"createdAt": "2025-09-06T11:00:00Z",
"updatedAt": "2025-09-06T11:00:00Z"
}
]

✅ Error Handling
Scenario	Error Code	Message
Invalid booking ID	404	"Booking not found"
Payment not found	404	"Payment not found"
Insufficient details	400	"Invalid payment request"
Payment gateway failure	502	"Payment could not be processed"

📌 Summary
The Payment Module ensures that every booking has a verified financial transaction.
It supports multiple payment methods, stores transaction IDs, and automatically confirms bookings once payment succeeds.


---

Do you want me to also **add example Postman requests** (with raw JSON & headers) for each payment endpoint in the `.md` file, so you can test it quickly?
