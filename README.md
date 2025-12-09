# Land Route Routing Service

A Spring Boot application that calculates the **shortest possible land route** between two countries, using official country border data.  
The service loads country information from a public JSON dataset and determines the land path using an efficient graph-based algorithm (BFS).

---

## 🚀 Features

- REST endpoint: **`/routing/{origin}/{destination}`**
- Returns the shortest valid land route between two countries
- Uses **cca3** country codes (ISO-3166 alpha-3)
- Returns HTTP **400** if no land route exists
- Loads country & border data from:
  https://raw.githubusercontent.com/mledoze/countries/master/countries.json
- Efficient BFS algorithm to determine routes
- Built with **Spring Boot** and **Maven**

---

## 📡 Example API Request

GET /routing/CZE/ITA


**Response:**
```json
{
  "route": ["CZE", "AUT", "ITA"]
}

Technology Stack

Java 21

Spring Boot

Maven

REST API

Jackson for JSON parsing

BFS graph search


src/main/java/com/example/routingservice/
│
├── controller/
│     └── RoutingController.java
│
├── service/
│     ├── RoutingService.java
│     ├── CountryLoader.java
│     └── RouteFinder.java
│
├── model/
│     └── Country.java
│
└── RoutingServiceApplication.java



Data Source

The service uses a public dataset containing all world countries and their land borders:

👉 https://raw.githubusercontent.com/mledoze/countries/master/countries.json

This file contains:

country code (cca3)

common name

list of bordering countries (borders)

🔍 How the Algorithm Works (Simplified)

Load all countries and their land borders.

Convert them into a graph:

Nodes = countries

Edges = borders

Use Breadth-First Search (BFS) to find the shortest route.

If a route exists → return it.

If not → return HTTP 400 Bad Request.

BFS guarantees the shortest path in an unweighted graph.

▶️ How to Build and Run the Project
1. Clone the repository
git clone https://github.com/Geofreytech/routing-service.git
cd routing-service

2. Build the project
mvn clean install

3. Run the application
mvn spring-boot:run


OR (after building):

java -jar target/routing-service-0.0.1-SNAPSHOT.jar

4. Test the endpoint

Example:

http://localhost:8080/routing/CZE/ITA

❌ Error Response Example

If no land route exists:

GET /routing/USA/JPN


Response:

{
  "error": "No land route found."
}


HTTP Status: 400 Bad Request

Geofrey Mwas (Geofreytech)
Backend Developer – Java & Spring Boot