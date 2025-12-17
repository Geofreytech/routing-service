# Land Route Calculator Service API

A Spring Boot application that calculates the shortest land route between two countries using border crossing data.

## Overview

This service utilizes a Breadth-First Search (BFS) algorithm to traverse country border data. It finds the shortest path (minimum number of border crossings) while handling edge cases such as islands and non-existent routes.

### Key Features:
* **Efficient Graph Traversal:** BFS algorithm with $O(V+E)$ time complexity.
* **In-memory Data Caching:** High-performance reads with $O(1)$ adjacency map lookups.
* **Comprehensive Error Handling:** Custom exceptions mapped to HTTP 400 for invalid routes or country codes.

## 🛠 Tech Stack

* **Language:** Java 21 (OpenJDK)
* **Framework:** Spring Boot 4.0.0
* **Build Tool:** Maven
* **Data Format:** JSON

## ⚙️ Usage

### 1. Prerequisites
Ensure you have the following installed:
* Java 21+
* Maven
* Git

### 2. Clone the Repository
```bash
git clone [https://github.com/geofreytech/country-routes-api.git](https://github.com/geofreytech/country-routes-api.git)
cd routing-service

mvn clean install
mvn spring-boot:run
Access the API at: http://localhost:8080

5. API Endpoints
Calculate Route
GET /routing/{origin}/{destination}

Example Request: http://localhost:8080/routing/CZE/ITA

Example Response (200 OK):

JSON

{
  "route": ["CZE", "AUT", "ITA"]
}
Parameters:

origin: The 3-letter CCA3 code of the starting country (e.g., CZE).

destination: The 3-letter CCA3 code of the destination (e.g., ITA).

Error Response (400 Bad Request): Returns HTTP 400 if no land route exists or if the country codes are invalid.

6. Example of Request
Bash

curl -v http://localhost:8080/routing/CZE/ITA
Example of Error (400) Response:

JSON

{
  "error": "No land crossing found between CZE and USA"
}
Architecture
The application follows a Domain-Driven layered architecture:

Controller Layer (api): Handles HTTP requests and response serialization.

Service Layer (domain.route): Contains the core BFS routing logic and business rules.

Data Layer (data): Manages data ingestion from the JSON source and provides the adjacency map.

Exception Layer (exception): Manages custom error types and global error responses.

🧪 Algorithm Details
The routing engine uses Breadth-First Search (BFS) to guarantee the shortest path in an unweighted graph (where every border crossing counts as one "step").

