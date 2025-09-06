# 🎬 Movie Module – Movie Booking System

The **Movie module** manages movies in the system. Each movie has a **title**, **description**, **runtime**, **certificate**, **genre**, **cast**, and **ratings** (calculated from feedbacks).

---

## 🔹 What Movies Can Do

1. **Add Movie** – create a new movie with details like title, description, runtime, etc.
2. **Update Movie** – update an existing movie’s information.
3. **Fetch Movie by ID** – get a single movie with its details and average rating.
4. **Search Movies** – search movies by title (case-insensitive).

---

## 🔹 Who Can Access

* **ADMIN / THEATER_OWNER** → can add and update movies.
* **USER (Customer)** → can only view and search movies.

---

## ✅ Why This is Important

* **Centralized Movie Data** – all theaters and shows reference these movies.
* **User Experience** – customers can search and view detailed movie info.
* **Ratings Integration** – feedbacks help calculate the average rating.
* **Scalability** – supports multiple genres, certificates, and casts.

---

# 🛠 Movie API Endpoints

**Base URL:** `http://localhost:8080`

**Headers:**

| Key          | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

---

### 1️⃣ Add Movie

**POST** `/movies`

**Request Example:**

```json
{
  "title": "Inception",
  "description": "A mind-bending sci-fi movie",
  "runtime": "PT2H28M",
  "certificate": "UA",
  "genre": "Sci-Fi",
  "castList": ["Leonardo DiCaprio", "Joseph Gordon-Levitt"]
}
```
Validation Rules:

Field	Required	Rule
title	Yes	Non-empty
description	Yes	Non-empty
runtime	Yes	ISO-8601 Duration format (PT2H28M)
certificate	Yes	Must be valid certificate (U, UA, A)
genre	Yes	Non-empty
castList	Yes	Must contain at least 1 actor

Response Example:

{
"movieId": "1493d021-193a-47d4-8051-9343f1070842",
"title": "Inception",
"description": "A mind-bending sci-fi movie",
"runtime": "PT2H28M",
"certificate": "UA",
"genre": "Sci-Fi",
"castList": ["Leonardo DiCaprio", "Joseph Gordon-Levitt"],
"avgRating": 0.0
}

2️⃣ Update Movie

PUT /movies/{movieId}

Request Example:

{
"title": "Inception (Updated)",
"description": "Sci-fi thriller by Christopher Nolan",
"runtime": "PT2H30M",
"certificate": "UA",
"genre": "Sci-Fi",
"castList": ["Leonardo DiCaprio", "Tom Hardy"]
}


Response Example:

{
"movieId": "1493d021-193a-47d4-8051-9343f1070842",
"title": "Inception (Updated)",
"description": "Sci-fi thriller by Christopher Nolan",
"runtime": "PT2H30M",
"certificate": "UA",
"genre": "Sci-Fi",
"castList": ["Leonardo DiCaprio", "Tom Hardy"],
"avgRating": 4.5
}

3️⃣ Fetch Movie by ID

GET /movies/{movieId}

Response Example:

{
"message": "Movie has been successfully fetched",
"data": {
"movieId": "1493d021-193a-47d4-8051-9343f1070842",
"title": "Inception",
"description": "A mind-bending sci-fi movie",
"runtime": "PT2H28M",
"certificate": "UA",
"genre": "Sci-Fi",
"castList": ["Leonardo DiCaprio", "Joseph Gordon-Levitt"],
"avgRating": 4.3
},
"statusCode": 200
}

4️⃣ Search Movies

GET /movies/search?search=Inception

Response Example:

{
"message": "Movies fetched Successfully",
"data": [
{
"movieId": "1493d021-193a-47d4-8051-9343f1070842",
"title": "Inception",
"description": "A mind-bending sci-fi movie",
"runtime": "PT2H28M",
"certificate": "UA",
"genre": "Sci-Fi",
"castList": ["Leonardo DiCaprio", "Joseph Gordon-Levitt"],
"avgRating": 4.3
}
],
"statusCode": 200
}

✅ Error Handling
Scenario	Error Code	Message
Invalid movie ID (GET/PUT)	404	"Movie not found"
Empty search	200	Returns []
Validation error (add/update)	400	Validation error message
✅ Quick Postman Testing Flow

POST /movies → Add a new movie.

PUT /movies/{id} → Update the movie details.

GET /movies/{id} → Fetch the movie by ID.

GET /movies/search?search={keyword} → Search movies by title.

📌 Summary
The Movie Module ensures movies are properly created, updated, fetched, and searched. It acts as the central source of truth for all theaters and shows, while also providing ratings for a better user experience.


---

Do you also want me to generate a **ready-to-import Postman Collection JSON file** for this Movie Module like I did with the